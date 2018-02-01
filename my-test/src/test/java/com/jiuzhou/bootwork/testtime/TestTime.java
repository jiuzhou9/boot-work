package com.jiuzhou.bootwork.testtime;

import junit.framework.TestCase;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/01/16
 */
public class TestTime extends TestCase {

    public void test(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
}
