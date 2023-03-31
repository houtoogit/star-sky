package com.star.sky.common.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@Slf4j
public class BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    public Object getBaseInfo(String access_token) {
        return redisTemplate.opsForValue().get(access_token);
    }

    public int getUUID(String access_token) {
        try {
            Object result = redisTemplate.opsForValue().get(access_token);
            return (int) mapper.convertValue(result, Map.class).get("uuid");
        } catch (Exception e) {
            log.error("object mapper parse error: {}", e.getMessage(), e);
            return 0;
        }
    }

}
