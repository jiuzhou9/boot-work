package com.jiuzhou.bootwork.testzip;

import org.junit.Test;

import java.time.LocalDate;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/08/21
 */
public class TestZip {

    @Test
    public void test(){
        ZipCompress zipCompress = new ZipCompress("/Users/wangjiuzhou/区域价格策略" + LocalDate.now().toString() + ".zip",
                                                  "/Users/wangjiuzhou/zzzz");
        try {
            zipCompress.zip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
