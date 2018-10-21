package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.cache.CompanyCacheManager;
import com.jiuzhou.bootwork.common.UUIDUtil;
import com.jiuzhou.bootwork.dao.mapper.CompanyMapper;
import com.jiuzhou.bootwork.dao.model.Company;
import com.jiuzhou.bootwork.dao.model.CompanyExample;
import com.jiuzhou.bootwork.dao.model.CompanyKey;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.service.CompanyService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;
import com.jiuzhou.bootwork.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/16
 */
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyCacheManager companyCacheManager;

    @Override
    public CompanyDTO getByCodeWithRedisAndDB(String code) throws ApiGateWayException {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(code.trim())){
            return null;
        }

        CompanyDTO companyDTO = companyCacheManager.get(code);
        if (companyDTO != null){
            return companyDTO;
        }

        CompanyExample companyExample = new CompanyExample();
        CompanyExample.Criteria criteria = companyExample.createCriteria();
        criteria.andCompanyCodeEqualTo(code);
        List<Company> companies = companyMapper.selectByExample(companyExample);

        if (CollectionUtils.isEmpty(companies)){
            return null;
        }else if (companies.size() > 1){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_CODE_QUERY_MANY_RESULTS);
        }else {
            companyDTO = new CompanyDTO();
            BeanUtils.copyProperties(companies.get(0), companyDTO);
            companyCacheManager.put(companyDTO);
            return companyDTO;
        }
    }

    @Override
    public void create(CompanyDTO companyDTO, Integer userId) throws ApiGateWayException {
        UserDTO userDTO = userService.getAvailableUserById(userId);
        String companyCode = userDTO.getCompanyCode();
        if (!StringUtils.isEmpty(companyCode) ){
            if (companyCode.equals(companyDTO.getCompanyCode())){
                return;
            }
        }
        if (StringUtils.isEmpty(companyDTO.getName()) || StringUtils.isEmpty(companyDTO.getName().trim())){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_NAME_IS_EMPTY);
        }
        if (StringUtils.isEmpty(companyDTO.getBusinessLicense()) || StringUtils.isEmpty(companyDTO.getBusinessLicense().trim())){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_BUSINESSLICENSE_IS_EMPTY);
        }
        companyDTO.setCompanyCode(UUIDUtil.generator());
//        Company company = new Company();
//        BeanUtils.copyProperties(companyDTO, company);
//        companyMapper.insertSelective(company);
        insertSelectiveRedisAndDB(companyDTO);
        userService.updateUserCompanyCode(userId, companyDTO.getCompanyCode());
        return;
    }

    /**
     * Redis + DB插入
     * @param dto
     */
    private void insertSelectiveRedisAndDB(CompanyDTO dto){
        Company company = new Company();
        BeanUtils.copyProperties(dto, company);
        int i = companyMapper.insertSelective(company);
        CompanyKey companyKey = new CompanyKey();
        companyKey.setId(i);
        Company tmp = companyMapper.selectByPrimaryKey(companyKey);
        BeanUtils.copyProperties(tmp, dto);
        companyCacheManager.putForever(dto);
    }

    @Override
    public List<CompanyDTO> getAllAvailable() {
        CompanyExample companyExample = new CompanyExample();
        List<Company> companies = companyMapper.selectByExample(companyExample);

        List<CompanyDTO> list = new ArrayList<>();
        for (Company company : companies) {
            CompanyDTO companyDTO = new CompanyDTO();
            BeanUtils.copyProperties(company, companyDTO);
            list.add(companyDTO);
        }
        return list;
    }
}
