package com.jiuzhou.bootwork.cache;

import com.jiuzhou.bootwork.cache.config.CacheManager;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/13
 *
 * 请求标识信息缓存
 */
@Component
@Slf4j
public class CompanyCacheManager extends CacheManager<CompanyDTO> {

    @Override
    protected String getKey(CompanyDTO companyDTO) {
        return getKey(companyDTO.getCompanyCode());
    }

    @Override
    protected String getKey(String endKey) {
        return getPreAndMiddleKey() + endKey;
    }

    @Override
    protected Class<CompanyDTO> getDTOClass() {
        return CompanyDTO.class;
    }

    public CompanyCacheManager() {
        super("COMPANY_");
    }
}
