package com.star.sky.member.api;

import com.star.sky.common.entities.I18n;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.I18nUtil;
import com.star.sky.common.utils.TokenUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;
import com.star.sky.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@Validated
@RestController
@RequestMapping("/access")
public class ApiAccessController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseResult<I18n> register(@Valid MemberBaseInfo baseInfo) {
        memberService.register(baseInfo);
        return ResponseResult.success(I18nUtil.getI18n("REGISTER_SUCCESS"));
    }

    @PostMapping(value = "/login")
    public ResponseResult<ResponseToken> login(@Valid MemberBaseInfo baseInfo) {
        return ResponseResult.success(new ResponseToken(TokenUtil.getToken(), baseInfo.getBase_phone()));
    }

    @PostMapping("/logout")
    public ResponseResult<String> logout() {
        return ResponseResult.success("Ok");
    }

}
