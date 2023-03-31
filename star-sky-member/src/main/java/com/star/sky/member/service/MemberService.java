package com.star.sky.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;

public interface MemberService extends IService<MemberBaseInfo> {

    void register(String phone, String password);

    ResponseToken login(String phone, String password);

    void logout(String access_token, int uuid);

    MemberBaseInfo queryByPhone(String phone);

}
