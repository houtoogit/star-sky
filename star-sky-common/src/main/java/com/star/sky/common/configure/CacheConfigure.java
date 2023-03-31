package com.star.sky.common.configure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheConfigure {

    public static final long CACHE_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 30;

    public static final String CACHE_TYPE_SET = "Set";
    public static final String CACHE_TYPE_HASH = "Hash";
    public static final String CACHE_TYPE_LIST = "List";
    public static final String CACHE_TYPE_STRING = "String";

    public static final String C_INDEX_LIST = "INDEX_LIST";


    public static final Map<String, String[]> CACHE_CONFIG = new ConcurrentHashMap<>();

    static {
        CACHE_CONFIG.put(C_INDEX_LIST, new String[]{"-1", CACHE_TYPE_STRING});
    }

}
