package com.star.sky.member.api;

import com.star.sky.common.common.BaseController;
import com.star.sky.common.entities.I18n;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.common.utils.I18nUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;
import com.star.sky.member.service.MemberService;
import com.star.sky.member.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@Validated
@RestController
@RequestMapping("/access")
public class ApiAccessController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseResult<I18n> register(@Valid MemberBaseInfo baseInfo) {
        memberService.register(baseInfo.getPhone(), baseInfo.getPassword());
        return ResponseResult.success(I18nUtil.getI18n("REGISTER_SUCCESS"));
    }

    @PostMapping("/login")
    public ResponseResult<ResponseToken> login(@Valid MemberBaseInfo baseInfo) {
        return ResponseResult.success(memberService.login(baseInfo.getPhone(), baseInfo.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseResult logout(@RequestHeader("X-Auth-Token") String access_token) {
        int uuid = this.getUUID(access_token);
        memberService.logout(access_token, uuid);
        return ResponseResult.success(null);
    }

}
