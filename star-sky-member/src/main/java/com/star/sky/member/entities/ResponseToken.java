package com.star.sky.member.entities;

import com.star.sky.common.configure.CacheConfigure;
import lombok.Data;

@Data
public class ResponseToken {

    private long expires_in;
    private String base_phone;
    private String token_type;
    private String access_token;

    public ResponseToken(String access_token, String base_phone) {
        this.base_phone = base_phone;
        this.token_type = "access_token";
        this.access_token = access_token;
        this.expires_in = CacheConfigure.CACHE_TOKEN_EXPIRE_TIME;
    }

}
