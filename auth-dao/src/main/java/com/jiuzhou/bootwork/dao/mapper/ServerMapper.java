package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.model.Server;
import com.jiuzhou.bootwork.dao.model.ServerExample;
import com.jiuzhou.bootwork.dao.model.ServerKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ServerMapper {
    long countByExample(ServerExample example);

    int deleteByExample(ServerExample example);

    int deleteByPrimaryKey(ServerKey key);

    int insert(Server record);

    int insertSelective(Server record);

    List<Server> selectByExample(ServerExample example);

    Server selectByPrimaryKey(ServerKey key);

    int updateByExampleSelective(@Param("record") Server record, @Param("example") ServerExample example);

    int updateByExample(@Param("record") Server record, @Param("example") ServerExample example);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);
}