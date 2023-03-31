package com.star.sky.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.sky.common.exception.StarSkyException;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.I18nUtil;
import com.star.sky.common.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Interceptor implements HandlerInterceptor {

    private static final int TIME_DIFF = 3000;

    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            String method = request.getMethod();
            String access_sign = request.getHeader("X-Auth-Sign");
            String access_token = request.getHeader("X-Auth-Token");

            // todo check timestamp
            long current_timestamp = System.currentTimeMillis();
            String encrypt_timestamp = access_sign.substring(32);
            long api_timestamp = Long.parseLong(RSAUtil.decrypt(encrypt_timestamp, RSAUtil.getPrivateKey(RSAUtil.PRIVATE_KEY)));
            if (current_timestamp - api_timestamp > TIME_DIFF) throw new StarSkyException(HttpStatus.UNAUTHORIZED);

            // todo check token
            if (StringUtils.isBlank(access_token)) throw new StarSkyException(HttpStatus.UNAUTHORIZED);
            Object cache_info = redisTemplate.opsForValue().get(access_token);
            if (cache_info == null) throw new StarSkyException(HttpStatus.UNAUTHORIZED);

            List<String> params = Stream.of(access_token, encrypt_timestamp).collect(Collectors.toList());

            if (GET.equals(method) || DELETE.equals(method)) {
                Map param = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                param.values().forEach(value -> params.add(String.valueOf(value)));
            }

            if (POST.equals(method) || PUT.equals(method)) {
                Map<String, String[]> param = request.getParameterMap();
                param.forEach((key, value) -> params.add(String.valueOf(value[0])));
            }

            // todo check sign
            Collections.sort(params);
            access_sign = access_sign.substring(0, 32);
            String current_sign = DigestUtils.md5DigestAsHex(params.stream().reduce("", (a, b) -> a + b).getBytes());
            if (!current_sign.equals(access_sign)) throw new StarSkyException(HttpStatus.UNAUTHORIZED);
            return true;
        } catch (Exception e) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().print(mapper.writeValueAsString(ResponseResult.failed(HttpStatus.UNAUTHORIZED, I18nUtil.getI18n("UNAUTHORIZED"))));
            response.getWriter().close();
            log.error("interceptor error: {}", e.getMessage(), e);
            return false;
        }
    }

}
