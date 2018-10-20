package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Accesskey;
import com.jiuzhou.bootwork.dao.model.AccesskeyExample;
import com.jiuzhou.bootwork.dao.model.AccesskeyKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccesskeyMapper {
    long countByExample(AccesskeyExample example);

    int deleteByExample(AccesskeyExample example);

    int deleteByPrimaryKey(AccesskeyKey key);

    int insert(Accesskey record);

    int insertSelective(Accesskey record);

    List<Accesskey> selectByExample(AccesskeyExample example);

    Accesskey selectByPrimaryKey(AccesskeyKey key);

    int updateByExampleSelective(@Param("record") Accesskey record, @Param("example") AccesskeyExample example);

    int updateByExample(@Param("record") Accesskey record, @Param("example") AccesskeyExample example);

    int updateByPrimaryKeySelective(Accesskey record);

    int updateByPrimaryKey(Accesskey record);
}