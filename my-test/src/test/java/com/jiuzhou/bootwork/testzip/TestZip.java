package com.jiuzhou.bootwork.testzip;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
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
