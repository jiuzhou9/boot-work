package com.jiuzhou.bootwork.excep;

import org.springframework.http.HttpStatus;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
public interface HttpError {

    /**
     * 获取应该返回的HTTP状态码
     *
     * @return Http状态码
     */
    HttpStatus getHttpStatus();

    /**
     * 返回内部自定义的状态码
     *
     * @return 错误码的内部定义
     */
    String getCode();

    /**
     * 返回错误的内部代码
     *
     * @return 错误信息的内部代码
     */
    String getMessage();
}
