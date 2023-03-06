package com.star.sky.common.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class JasyptUtil {

    public static final String PBE_ALGORITHMS_MD5_DES = "PBEWITHMD5ANDDES";
    public static final String PBE_WITH_HMAC_SHA512_AES_256 = "PBEWITHHMACSHA512ANDAES_256";

    private PooledPBEStringEncryptor getEncryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        config.setPoolSize("1");
        config.setPassword(password);
        config.setProviderName("SunJCE");
        config.setStringOutputType("base64");
        config.setKeyObtentionIterations("1000");
        config.setAlgorithm(PBE_WITH_HMAC_SHA512_AES_256);
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        encryptor.setConfig(config);
        return encryptor;
    }

    public String encrypt(String encryptedStr, String password) {
        return getEncryptor(password).encrypt(encryptedStr);
    }

    public String decrypt(String decryptStr, String password) {
        return getEncryptor(password).decrypt(decryptStr);
    }

}