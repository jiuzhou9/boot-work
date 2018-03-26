package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.ServerDto;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/25
 */
public interface ServerService {

    ServerDto insert(ServerDto serverDto) throws Exception;
}
