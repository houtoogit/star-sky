package com.star.sky.member.api;

import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.TokenUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;
import com.star.sky.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/access")
public class ApiAccessController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseResult<String> register() {
        return ResponseResult.failed(HttpStatus.GATEWAY_TIMEOUT);
    }

    @PostMapping(value = "/login")
    public ResponseResult<ResponseToken> login(MemberBaseInfo baseInfo) {
        MemberBaseInfo info = memberService.getInfo(baseInfo);
        return ResponseResult.success(new ResponseToken(TokenUtil.getToken(), info.getBase_phone(), 300));
    }

    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        return ResponseResult.success("Ok");
    }

}
