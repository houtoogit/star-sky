package com.star.sky.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.sky.common.exception.StarSkyException;
import com.star.sky.common.utils.I18nUtil;
import com.star.sky.common.utils.UUIDUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.mapper.MemberBaseInfoMapper;
import com.star.sky.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberBaseInfoMapper, MemberBaseInfo> implements MemberService {

    @Autowired
    private MemberService memberService;

    private MemberBaseInfo queryByUUID(int uuid) {
        return memberService.getOne(Wrappers.<MemberBaseInfo>lambdaQuery().eq(MemberBaseInfo::getBase_uuid, uuid), false);
    }

    private MemberBaseInfo queryByPhone(String base_phone) {
        return memberService.getOne(Wrappers.<MemberBaseInfo>lambdaQuery().eq(MemberBaseInfo::getBase_phone, base_phone), false);
    }

    @Override
    public void register(MemberBaseInfo baseInfo) {
        int uuid;
        String base_phone = baseInfo.getBase_phone();
        String base_password = baseInfo.getBase_password();

        do {
            uuid = UUIDUtil.nextUUID();
        } while (this.queryByUUID(uuid) != null);

        if (this.queryByPhone(base_phone) != null) {
            throw new StarSkyException(HttpStatus.BAD_REQUEST, I18nUtil.getI18n("PHONE_NUMBER_ALREADY_EXIST"));
        }

        baseInfo.setBase_uuid(uuid);
        baseInfo.setBase_phone(base_phone);
        baseInfo.setBase_register_time(new Date());
        baseInfo.setBase_password(DigestUtils.md5DigestAsHex(base_password.getBytes()));
        memberService.save(baseInfo);
    }

}
