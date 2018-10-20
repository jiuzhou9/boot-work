package com.jiuzhou.bootwork.testabstract;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/10
 */
public abstract class TestAbstract {

    protected String pre = "pre";

    protected String middle;

    public String getPreMiddle() {
        return this.pre + this.middle;
    }

    /**
     * 强制要求子类必须通过此构造方法进行初始化对象
     * @param middle
     */
    public TestAbstract(String middle) {
        this.middle = middle;
    }
}
