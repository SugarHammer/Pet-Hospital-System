package com.fjp.util;

import java.util.HashMap;
import java.util.Map;

public class BaseContextHolder {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key, Object value) {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new HashMap<>());
        }
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }

    public static Object get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>();
        }
        Object res = map.get(key);
        THREAD_LOCAL.set(map);
        return res;
    }
}
