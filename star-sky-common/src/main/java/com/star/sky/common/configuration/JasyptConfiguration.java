package com.star.sky.common.configuration;

import lombok.NoArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class JasyptConfiguration implements StringEncryptor {

    public static final String PBE_ALGORITHMS_MD5_DES = "PBEWITHMD5ANDDES";
    public static final String PBE_WITH_HMAC_SHA512_AES_256 = "PBEWITHHMACSHA512ANDAES_256";

    @Value("${jasypt.encryptor.password}")
    private String password;

    private String algorithm = PBE_WITH_HMAC_SHA512_AES_256;

    @Override
    public String encrypt(String encryptedStr) {
        return this.getEncryptor().encrypt(encryptedStr);
    }

    @Override
    public String decrypt(String decryptStr) {
        return this.getEncryptor().decrypt(decryptStr);
    }

    private PooledPBEStringEncryptor getEncryptor() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        config.setPoolSize("1");
        config.setPassword(password);
        config.setAlgorithm(algorithm);
        config.setProviderName("SunJCE");
        config.setStringOutputType("base64");
        config.setKeyObtentionIterations("1000");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean("jasyptStringEncryptor")
    public JasyptConfiguration getJasyptConfiguration() {
        return new JasyptConfiguration();
    }

}
