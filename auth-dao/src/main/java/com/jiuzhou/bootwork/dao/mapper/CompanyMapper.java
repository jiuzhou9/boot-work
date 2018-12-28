package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Company;
import com.jiuzhou.bootwork.dao.model.CompanyExample;
import com.jiuzhou.bootwork.dao.model.CompanyKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {
    long countByExample(CompanyExample example);

    int deleteByExample(CompanyExample example);

    int deleteByPrimaryKey(CompanyKey key);

    int insert(Company record);

    int insertSelective(Company record);

    List<Company> selectByExample(CompanyExample example);

    Company selectByPrimaryKey(CompanyKey key);

    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}