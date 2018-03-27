package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.App;
import com.jiuzhou.bootwork.dao.model.AppExample;
import com.jiuzhou.bootwork.dao.model.AppKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMapper {
    long countByExample(AppExample example);

    int deleteByExample(AppExample example);

    int deleteByPrimaryKey(AppKey key);

    int insert(App record);

    int insertSelective(App record);

    List<App> selectByExample(AppExample example);

    App selectByPrimaryKey(AppKey key);

    int updateByExampleSelective(@Param("record") App record, @Param("example") AppExample example);

    int updateByExample(@Param("record") App record, @Param("example") AppExample example);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
}