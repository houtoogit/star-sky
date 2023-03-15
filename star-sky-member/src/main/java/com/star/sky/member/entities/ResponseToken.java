package com.star.sky.member.entities;

import lombok.Data;

@Data
public class ResponseToken {

    private long expires_in;
    private String username;
    private String token_type;
    private String access_token;

    public ResponseToken(String access_token, String username, long expires_in) {
        this.username = username;
        this.expires_in = expires_in;
        this.token_type = "access_token";
        this.access_token = access_token;
    }
}
