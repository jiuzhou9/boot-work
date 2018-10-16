package com.jiuzhou.bootwork.testabstract;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/10/10
 */
public class TestSon extends TestAbstract {

    public static void main(String[] args) {
        TestSon testSon = new TestSon();
        testSon.middle = "middle";
        String s = testSon.get();
        System.out.println(s);
    }
}
