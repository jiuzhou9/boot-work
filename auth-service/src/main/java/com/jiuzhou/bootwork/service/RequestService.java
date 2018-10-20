package com.jiuzhou.bootwork.service;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 *
 * 请求服务
 */
public interface RequestService {

    /**
     * 获取一个requestId
     * 废弃原因：前缀由每一个api自己加
     *
     * @param pre 服务名称前缀
     * @return requestId
     */
    String getRequestId(String pre);

    /**
     * 获取一个requestId
     * @return requestId
     */
    @Deprecated
    String getRequestId();
}
