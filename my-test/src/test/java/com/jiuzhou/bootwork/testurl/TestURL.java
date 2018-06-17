package com.jiuzhou.bootwork.testurl;

import junit.framework.TestCase;
import org.springframework.util.AntPathMatcher;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/06/14
 */
public class TestURL extends TestCase {

    /**
     * URL映射器，判断URL是否符合一个映射规则
     */
    public void test(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/hello/{id}", "/hello/1");
        System.out.println(match);
    }
}
