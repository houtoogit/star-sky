package com.star.sky.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.star.sky.common.exception.StarSkyException;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            String method = request.getMethod();
            String access_sign = request.getHeader("X-Auth-Sign");
            String access_token = request.getHeader("X-Auth-Token");

            if (StringUtils.isBlank(access_token)) throw new StarSkyException(HttpStatus.UNAUTHORIZED);

            long current_timestamp = System.currentTimeMillis();
            long api_timestamp = Long.parseLong(RSAUtil.decrypt(access_token.substring(43), RSAUtil.getPrivateKey(RSAUtil.PRIVATE_KEY)));

            if (current_timestamp - api_timestamp > TIME_DIFF) throw new StarSkyException(HttpStatus.UNAUTHORIZED);

            // todo check token
            access_token = access_token.substring(0, 43);

            List<String> signs = Stream.of(access_token, String.valueOf(api_timestamp)).collect(Collectors.toList());

            if (GET.equals(method)) {
                Map params = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params.values().forEach(value -> signs.add(String.valueOf(value)));
            }

            if (POST.equals(method) || PUT.equals(method) || DELETE.equals(method)) {
                Map<String, String[]> params = request.getParameterMap();
                params.forEach((key, value) -> signs.add(String.valueOf(value[0])));
            }

            Collections.sort(signs);
            String curr_sign = DigestUtils.md5DigestAsHex(signs.stream().reduce("", (a, b) -> a + b).getBytes());
            if (!curr_sign.equals(access_sign)) throw new StarSkyException(HttpStatus.UNAUTHORIZED);
            return true;
        } catch (Exception e) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().print(mapper.writeValueAsString(ResponseResult.failed(HttpStatus.UNAUTHORIZED)));
            response.getWriter().close();
            log.error("interceptor error: {}", e.getMessage(), e);
            return false;
        }
    }

}
