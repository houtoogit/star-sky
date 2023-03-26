package com.star.sky.member.service;

import com.star.sky.member.entities.MemberBaseInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Cacheable(key = "T(com.star.sky.common.configure.CacheConfigure).C_TOKEN.concat('-').concat(#baseInfo.base_phone)")
    public MemberBaseInfo getInfo(MemberBaseInfo baseInfo) {
        return baseInfo;
    }

}
