package com.jiuzhou.bootwork.testenum;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/09/14
 */
public enum EnumD1 {
    //括号外的 name 括号里的是value
    geo("0000");

    private String pre;

    EnumD1(String pre) {
        this.pre = pre;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a1","b");
        map.put("a2","b");
        map.put("a3","b");
        map.put("a4","b");
        map.put("a5","b");
        System.out.println(JSON.toJSONString(map));
//        EnumD1 geo = EnumD1.valueOf("geo");
//        System.out.println(geo.pre);
    }
}
