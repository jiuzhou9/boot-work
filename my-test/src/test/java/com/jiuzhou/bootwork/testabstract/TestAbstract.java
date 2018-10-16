package com.jiuzhou.bootwork.testabstract;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/10/10
 */
public abstract class TestAbstract {

    protected String pre = "a";

    protected String middle;

    public String get() {
        return this.pre + this.middle;
    }
}