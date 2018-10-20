package com.jiuzhou.bootwork.result;

/**
 * 业务层的返回结果
 *
 * @author wangchuan (wangchuan@shanshu.ai)
 * @date 2017/11/22
 */
public class BaseResult<T> {
    /**
     * 结果返回的数据域
     */
    private T data;
    /**
     * 结果有错误信息时候，包含的对象
     */
    private Error error;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return this.error == null;
    }
}
