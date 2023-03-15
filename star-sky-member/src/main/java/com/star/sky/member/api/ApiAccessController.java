package com.star.sky.member.api;

import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/access")
public class ApiAccessController {

    @PostMapping("/register")
    public ResponseResult<String> register() {
        return ResponseResult.failed(HttpStatus.GATEWAY_TIMEOUT);
    }

    @PostMapping("/login")
    public ResponseResult<Long> login(@RequestHeader("Access-Token") String access_token) throws Exception {
        log.info("access_token is: {}", access_token);
        String token = RSAUtil.decrypt(access_token, RSAUtil.getPrivateKey(RSAUtil.PRIVATE_KEY));
        log.info("token is: {}", token);
        return ResponseResult.success(System.currentTimeMillis());
    }

    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        return ResponseResult.success("Ok");
    }

}
