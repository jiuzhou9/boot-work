package com.jiuzhou.bootwork.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonUtil {
    private static final SimplePropertyPreFilter FILTER = new SimplePropertyPreFilter();

    static {
        FILTER.getExcludes().add("apiMethod");
        FILTER.getExcludes().add("responseClass");
    }

    public static String toJson(Object o) {

        try {
            return JSON.toJSONString(o, FILTER);
        } catch (Exception e) {
            return "";
        }
    }

    public static JSONObject fromJson(String json) {
        return JSON.parseObject(json);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("paser json error " + json, e);
        }
    }
}
