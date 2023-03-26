package com.star.sky.common.utils;

import com.star.sky.common.entities.I18n;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class I18nUtil {

    public static I18n getI18n(String i18nKey) {
        Locale en_US = new Locale("en", "US");
        Locale zh_CN = new Locale("zh", "CN");
        ResourceBundle msg_en = ResourceBundle.getBundle("i18n", en_US);
        ResourceBundle msg_zh = ResourceBundle.getBundle("i18n", zh_CN);
        return new I18n(getMsgZh(msg_zh, i18nKey), msg_en.getString(i18nKey));
    }

    private static String getMsgZh(ResourceBundle bundle, String i18nKey) {
        try {
            return new String(bundle.getString(i18nKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("set coding error: {}", e.getMessage());
            return bundle.getString(i18nKey);
        }
    }

}
