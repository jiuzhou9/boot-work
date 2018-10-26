package com.jiuzhou.bootwork.service;

import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
public interface CompanyService {

    /**
     * 条件查询
     * @param code 编码
     * @return 唯一一个公司对象
     * @throws ApiGateWayException 当查询结果为多条数据的时候抛异常
     */
    CompanyDTO getByCodeWithRedisAndDB(String code) throws ApiGateWayException;

    /**
     * 创建公司
     * @param companyDTO
     * @param userId
     */
    void create(CompanyDTO companyDTO, Integer userId) throws ApiGateWayException;


    List<CompanyDTO> getAllAvailable();
}
