package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.service.dto.QuotaDTO;
import com.jiuzhou.bootwork.service.dto.UpdateQuotaDTO;
import com.jiuzhou.bootwork.excep_new.ApiGateWayException;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
public interface QuotaService {

    /**
     * 根据key和资源编码条件查询额度数据,这个数据是有效的
     * @param key key
     * @param resourceCode 资源编码
     * @return 有效的唯一一条额度数据
     */
    QuotaDTO getByKeyAndResourceCodeWithRedisAndDB(String key, String resourceCode) throws ApiGateWayException;

    /**
     * 校验额度是否合法
     * 1.没有额度限制
     * 2.时间额度
     * 3.调用次数额度
     * 4.车次额度
     *
     * @param key key
     * @param resourceCode 资源code
     * return 返回额度信息，如果当前请求需要回调更新额度，那么返回对象是非空的，但是如果当前请求不需要回调更新额度，那么返回空对象
     */
    QuotaDTO checkQuota(String key, String resourceCode) throws ApiGateWayException;


    /**
     * 获取所有有效的额度信息
     * @return
     */
    List<QuotaDTO> getAllAvailable();


    /**
     * 更新额度
     * @param updateQuotaDTO
     */
    void updateRedisQuota(UpdateQuotaDTO updateQuotaDTO) throws ApiGateWayException;
}
