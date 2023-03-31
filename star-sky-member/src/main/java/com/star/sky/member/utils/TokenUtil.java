package com.star.sky.member.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class TokenUtil {

    public static String getRandomStr(int num) {
        return RandomStringUtils.randomAlphanumeric(num);
    }

    public static String getToken() {
        return RandomStringUtils.randomNumeric(10) + "_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
