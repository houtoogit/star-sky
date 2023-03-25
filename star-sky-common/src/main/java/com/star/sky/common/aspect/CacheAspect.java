package com.star.sky.common.aspect;

import com.star.sky.common.configure.CacheConfigure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void queryCache() {
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.CachePut)")
    public void updateCache() {
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.CacheEvict)")
    public void cleanCache() {
    }

    @Around(value = "queryCache()")
    public Object queryCacheAspect(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        Cacheable annotation = method.getAnnotation(Cacheable.class);

        String key = this.getCacheKey(point, method, annotation.key());
        String prefix_key = this.getPrefixKey(key);
        if (CacheConfigure.C_TOKEN.equals(prefix_key)) key = this.getSuffixKey(key);

        Object result = redisTemplate.opsForValue().get(key);

        if (result == null) {
            result = point.proceed();
            String[] cache_configs = CacheConfigure.CACHE_CONFIG.get(prefix_key);
            this.execRedisOperation(cache_configs[1], key, result, Long.parseLong(cache_configs[0]));
        }

        return result;
    }

    @Around(value = "updateCache()")
    public Object updateCacheAspect(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        CachePut annotation = method.getAnnotation(CachePut.class);

        String key = this.getCacheKey(point, method, annotation.key());
        String prefix_key = this.getPrefixKey(key);
        if (CacheConfigure.C_TOKEN.equals(prefix_key)) key = this.getSuffixKey(key);

        Object result = point.proceed();
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        String[] cache_configs = CacheConfigure.CACHE_CONFIG.get(prefix_key);
        this.execRedisOperation(cache_configs[1], key, result, expire);

        return result;
    }

    @Around(value = "cleanCache()")
    public Object cleanCacheAspect(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        CacheEvict annotation = method.getAnnotation(CacheEvict.class);

        String key = this.getCacheKey(point, method, annotation.key());
        String prefix_key = this.getPrefixKey(key);
        if (CacheConfigure.C_TOKEN.equals(prefix_key)) key = this.getSuffixKey(key);

        Object result = point.proceed();
        redisTemplate.delete(key);

        return result;
    }

    private void execRedisOperation(String cache_type, String key, Object result, Long expire) {
        if (CacheConfigure.CACHE_TYPE_STRING.equals(cache_type)) {
            redisTemplate.opsForValue().set(key, result);
        }
        if (CacheConfigure.CACHE_TYPE_HASH.equals(cache_type)) {
            redisTemplate.opsForHash().putAll(key, (Map) result);
        }
        if (CacheConfigure.CACHE_TYPE_LIST.equals(cache_type)) {
            redisTemplate.opsForList().rightPush(key, result);
        }
        if (CacheConfigure.CACHE_TYPE_SET.equals(cache_type)) {
            redisTemplate.opsForSet().add(key, result);
        }
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    private String getPrefixKey(String key) {
        return key.substring(0, key.indexOf("-"));
    }

    private String getSuffixKey(String key) {
        return key.substring(key.indexOf("-") + 1, key.length());
    }

    private Method getMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

    private String getCacheKey(ProceedingJoinPoint point, Method method, String expressionKey) {
        Object[] args = point.getArgs();
        EvaluationContext context = new StandardEvaluationContext();
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

        String[] params = discoverer.getParameterNames(method);
        Expression expression = expressionParser.parseExpression(expressionKey);

        for (int i = 0; i < params.length; i++) {
            context.setVariable(params[i], args[i]);
        }

        return (String) expression.getValue(context);
    }

}
