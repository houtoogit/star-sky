package com.star.sky.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class JasyptUtilTest {

    @InjectMocks
    private JasyptUtil jasyptUtil;

    @Test
    public void testEncryptAndDecrypt() {
        String encrypt = jasyptUtil.encrypt("root", "OriginalMix");
        String decrypt = jasyptUtil.decrypt(encrypt, "OriginalMix");
        log.info("encrypt is: {}", encrypt);
        log.info("decrypt is: {}", decrypt);
    }

}
