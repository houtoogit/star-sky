package com.star.sky.common.configure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheConfigure {

    public static final String CACHE_TYPE_SET = "Set";
    public static final String CACHE_TYPE_HASH = "Hash";
    public static final String CACHE_TYPE_LIST = "List";
    public static final String CACHE_TYPE_STRING = "String";

    public static final String C_TOKEN = "TOKEN";
    public static final String C_INDEX_LIST = "INDEX_LIST";


    public static final Map<String, String[]> CACHE_CONFIG = new ConcurrentHashMap<>();

    static {
        CACHE_CONFIG.put(C_TOKEN, new String[]{"2592000", CACHE_TYPE_STRING});
        CACHE_CONFIG.put(C_INDEX_LIST, new String[]{"-1", CACHE_TYPE_STRING});
    }

}
