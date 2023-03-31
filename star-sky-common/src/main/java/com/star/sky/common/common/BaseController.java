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

    public Object getBaseInfo(String access_token) {
        return redisTemplate.opsForValue().get(access_token);
    }

    public int getUUID(String access_token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object result = redisTemplate.opsForValue().get(access_token);
            Map map = mapper.convertValue(result, Map.class);
            return (int) map.get("uuid");
        } catch (Exception e) {
            
            return 0;
        }
    }

}
