package com.star.sky.member.api;

import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.TokenUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;
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
    public ResponseResult<ResponseToken> login(MemberBaseInfo baseInfo) {
        return ResponseResult.success(new ResponseToken(TokenUtil.getToken(), baseInfo.getBase_phone(), 300));
    }

    @GetMapping("/get-member-info/{id}/{name}")
    public ResponseResult<MemberBaseInfo> getMemberInfo() {
//        String sign = DigestUtils.md5DigestAsHex((token + timestamp).getBytes());
//        log.info("sign is: {}", sign);

        return ResponseResult.success(new MemberBaseInfo());
    }

    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        return ResponseResult.success("Ok");
    }

}
