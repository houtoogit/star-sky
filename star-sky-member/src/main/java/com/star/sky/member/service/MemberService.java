package com.star.sky.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.star.sky.member.entities.MemberBaseInfo;

public interface MemberService extends IService<MemberBaseInfo> {

    void register(MemberBaseInfo baseInfo);

}
