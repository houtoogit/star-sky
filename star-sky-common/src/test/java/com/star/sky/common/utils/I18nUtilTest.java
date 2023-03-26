package com.star.sky.common.utils;

import com.star.sky.common.entities.I18n;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class I18nUtilTest {

    @Test
    public void testGetI18n() {
        I18n i18n = I18nUtil.getI18n("REGISTER_SUCCESS");
        log.info("zh_CN is: {}", i18n.getZh_CN());
        log.info("en_US is: {}", i18n.getEn_US());
    }

}
