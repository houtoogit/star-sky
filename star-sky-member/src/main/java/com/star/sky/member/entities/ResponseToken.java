package com.star.sky.member.entities;

import lombok.Data;

@Data
public class ResponseToken {

    private long expires_in;
    private String base_phone;
    private String token_type;
    private String access_token;

    public ResponseToken(String access_token, String base_phone, long expires_in) {
        this.base_phone = base_phone;
        this.expires_in = expires_in;
        this.token_type = "access_token";
        this.access_token = access_token;
    }

}
