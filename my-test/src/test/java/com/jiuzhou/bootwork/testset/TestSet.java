package com.jiuzhou.bootwork.testset;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/07
 */
public class TestSet extends TestCase {

    /**
     * 对数组进行去重
     */
    public void testSet(){
        int[] array = {1, 2, 1};
        Set<Integer> hashSet = new HashSet<>();
        for (int i : array) {
            hashSet.add(array[i]);
        }
        System.out.println(hashSet);
    }

}
