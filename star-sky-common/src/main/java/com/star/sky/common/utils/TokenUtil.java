package com.star.sky.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtil {

    public static String getRandomStr(int num) {
        return RandomStringUtils.randomAlphanumeric(num);
    }

    public static String getToken() {
        return RandomStringUtils.randomNumeric(10) + "_" + RandomStringUtils.randomAlphanumeric(32);
    }

}
