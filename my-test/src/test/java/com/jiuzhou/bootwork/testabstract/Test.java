package com.jiuzhou.bootwork.testabstract;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/10/19
 */
public class Test {

    public static void main(String[] args) {
        TestSon testSon = new TestSon();
        String preMiddle = testSon.getPreMiddle();
        System.out.println(preMiddle);
    }
}
