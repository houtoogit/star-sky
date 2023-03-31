package com.star.sky.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.sky.common.configure.CacheConfigure;
import com.star.sky.common.exception.StarSkyException;
import com.star.sky.common.utils.I18nUtil;
import com.star.sky.member.entities.MemberBaseInfo;
import com.star.sky.member.entities.ResponseToken;
import com.star.sky.member.mapper.MemberBaseInfoMapper;
import com.star.sky.member.service.MemberService;
import com.star.sky.member.utils.TokenUtil;
import com.star.sky.member.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberBaseInfoMapper, MemberBaseInfo> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    private String getMD5Password(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    @Override
    public MemberBaseInfo queryByPhone(String base_phone) {
        return this.getOne(Wrappers.<MemberBaseInfo>lambdaQuery().eq(MemberBaseInfo::getPhone, base_phone), false);
    }

    @Override
    public void register(String phone, String password) {
        int uuid;
        MemberBaseInfo baseInfo = new MemberBaseInfo();

        do {
            uuid = UUIDUtil.nextUUID();
        } while (this.getOne(Wrappers.<MemberBaseInfo>lambdaQuery().eq(MemberBaseInfo::getUuid, uuid), false) != null);

        if (this.queryByPhone(phone) != null) {
            throw new StarSkyException(HttpStatus.BAD_REQUEST, I18nUtil.getI18n("PHONE_NUMBER_ALREADY_EXIST"));
        }

        baseInfo.setUuid(uuid);
        baseInfo.setPhone(phone);
        baseInfo.setRegisterTime(LocalDateTime.now());
        baseInfo.setPassword(this.getMD5Password(password));
        this.save(baseInfo);
    }

    @Override
    public ResponseToken login(String phone, String password) {
        MemberBaseInfo baseInfo = this.getOne(Wrappers.<MemberBaseInfo>lambdaQuery().eq(MemberBaseInfo::getPhone, phone)
                .eq(MemberBaseInfo::getPassword, this.getMD5Password(password)), false);
        if (baseInfo == null)
            throw new StarSkyException(HttpStatus.BAD_REQUEST, I18nUtil.getI18n("PHONE_NUMBER_OR_PASSWORD_ERROR"));

        String access_token = TokenUtil.getToken();
        String uuid = String.valueOf(baseInfo.getUuid());

        // 只允许一个用户登录 - 挤掉上次登录的用户
        String old_token = (String) redisTemplate.opsForValue().get(uuid);
        if (StringUtils.isNotBlank(old_token)) redisTemplate.delete(old_token);

        redisTemplate.opsForValue().set(uuid, access_token, CacheConfigure.CACHE_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(access_token, baseInfo, CacheConfigure.CACHE_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

        return new ResponseToken(access_token, phone);
    }

    @Override
    public void logout(String access_token, int uuid) {
        redisTemplate.delete(access_token);
        redisTemplate.delete(String.valueOf(uuid));
    }

}
