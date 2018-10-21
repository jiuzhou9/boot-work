package com.jiuzhou.bootwork.controller.base;

import com.jiuzhou.bootwork.excep.HttpError;
import com.jiuzhou.bootwork.result.Result;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
public class ErrorResult extends Result {

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResult(HttpError httpError){
        this.code = httpError.getCode();
        this.message = httpError.getMessage();
    }
}
