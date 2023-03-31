package com.star.sky.member.api;

import com.star.sky.common.common.BaseController;
import com.star.sky.common.result.ResponseResult;
import com.star.sky.member.entities.MemberBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("member")
public class ApiMemberController extends BaseController {

    @GetMapping("queryInfo")
    public ResponseResult<MemberBaseInfo> getMemberBaseInfo(@RequestHeader("X-Auth-Token") String access_token) {
        return ResponseResult.success((MemberBaseInfo) this.getBaseInfo(access_token));
    }

}
