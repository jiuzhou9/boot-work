package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.service.dto.MatchApiDTO;
import com.jiuzhou.bootwork.service.dto.ResourceDTO;
import com.jiuzhou.bootwork.excep_new.ApiGateWayException;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 *
 * todo 创建Resource接口  缓存Redis
 */
public interface ResourceService {

    /**
     * 创建Resource
     * @param resourceDTO
     * @return
     */
    int createResource(ResourceDTO resourceDTO) throws ApiGateWayException;

    /**
     * 查询所有的有效的资源
     * @return
     */
    List<ResourceDTO> getAvailable();

    /**
     * 获取资源集合，准备缓存Redis
     * @return
     */
    List<ResourceCacheDTO> getResourcesToCache();

    /**
     * 根据请求的api路径匹配资源code
     * @see ResourceService#matchWithAnt(String, RequestMethod)
     * @param reqApiPath 请求的api路径
     * @param requestMethod 请求方式
     * @return api资源code
     */
    @Deprecated
    String match(String reqApiPath, RequestMethod requestMethod);

    /**
     * 根据请求的api路径匹配资源code
     * @param reqApiPath 客户端请求的api路径
     * @param requestMethod 客户端请求方式
     * @return 匹配结果
     */
    MatchApiDTO matchWithAnt(String reqApiPath, RequestMethod requestMethod) throws ApiGateWayException;

    /**
     * 更新指定资源信息，同时更新Redis缓存
     * 根据目前的业务场景，应该不会有这种操作所以延迟开发
     *
     * @param code
     * @param resource
     * @return
     */
//    boolean update(String code, Resource resource);
}
