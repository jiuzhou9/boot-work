package com.jiuzhou.bootwork.common;

import java.util.UUID;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
public class UUIDUtil {

    /**
     * 生成32位UUID字符串
     * @return 32位UUID
     */
    public static String generator(){

        UUID uuid = UUID.randomUUID();

        String s = uuid.toString();

        s = s.replaceAll("-", "");

        return s;
    }

}
