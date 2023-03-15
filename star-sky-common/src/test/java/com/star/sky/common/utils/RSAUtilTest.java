package com.star.sky.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

import java.security.KeyPair;

@Slf4j
public class RSAUtilTest {

    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJ34JYPyRIUlKe8A/Bnxr/rfivWP5WeFP3G6l+1ng4ZJB+gXjmq9Ylnj+6E8UZ9R0ZUcFOXKxZnSmZ3WYYQ7ZeECAwEAAQ==";
    private static final String PRIVATE_KEY = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAnfglg/JEhSUp7wD8GfGv+t+K9Y/lZ4U/cbqX7WeDhkkH6BeOar1iWeP7oTxRn1HRlRwU5crFmdKZndZhhDtl4QIDAQABAkAKrPN0GFJTITDVSx6+bhvze1n7wG7DcTjZab/Mtnsn/vo52uQwow7Bws8ote1Y50Mr0bMYz1UIWNNG2F1vzKABAiEA2gAt3jERPGjLwmDFTggjglHFFippopvYw2egdh3rHuECIQC5gTGl2TBCGe1XDAjXnSgtrRCoAE+h02KJLqjPtBwnAQIgO5847D/3OtJeh/b3f3PyYj4C7SEEaxi8nBTHagacu4ECIBDb0OtFpcEcIhm7GkKeIbyk8dKS1KncUCQvfWAC+lwBAiB/NvHywoDGEJY7xWAeSnWGxmFBQiAgJ9lOW1/+AMz0bg==";

    @Test
    public void testGetPublicKeyAndPrivateKey() throws Exception {
        KeyPair keyPair = RSAUtil.getKeyPair();
        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        log.info("publicKey is: {}", publicKey);
        log.info("privateKey is: {}", privateKey);
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        String data = String.valueOf(System.currentTimeMillis());
        String encryptData = RSAUtil.encrypt(data, RSAUtil.getPublicKey(PUBLIC_KEY));
        log.info("encrypt is: {}", encryptData);

        String decryptData = RSAUtil.decrypt(encryptData, RSAUtil.getPrivateKey(PRIVATE_KEY));
        log.info("decrypt is: {}", decryptData);

        String sign = RSAUtil.sign(data, RSAUtil.getPrivateKey(PRIVATE_KEY));
        boolean result = RSAUtil.verify(data, RSAUtil.getPublicKey(PUBLIC_KEY), sign);
        log.info("check sign result is: {}", result);
    }

}
