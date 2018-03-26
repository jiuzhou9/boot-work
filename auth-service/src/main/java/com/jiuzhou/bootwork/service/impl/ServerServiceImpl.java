package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.ServerMapper;
import com.jiuzhou.bootwork.dao.model.Server;
import com.jiuzhou.bootwork.dao.model.ServerExample;
import com.jiuzhou.bootwork.service.ServerService;
import com.jiuzhou.bootwork.service.dto.ServerDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/25
 */
@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerMapper serverMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ServerDto insert(ServerDto serverDto) throws Exception {
        Server server = new Server();
        BeanUtils.copyProperties(serverDto, server);
        validateInsert(serverDto);
        int insert = serverMapper.insert(server);
        return serverDto;
    }

    /**
     * 插入规则校验
     * @param serverDto
     */
    private void validateInsert(ServerDto serverDto) throws Exception {
        if (serverDto == null){
            throw new Exception("服务信息为空");
        }
        String name = serverDto.getName();
        if (StringUtils.isEmpty(name)){
            throw new Exception("服务name为空");
        }
        String description = serverDto.getDescription();
        if (StringUtils.isEmpty(description)){
            throw new Exception("服务描述为空");
        }

        ServerExample serverExample = new ServerExample();
        ServerExample.Criteria criteria = serverExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Server> servers = serverMapper.selectByExample(serverExample);
        if (!CollectionUtils.isEmpty(servers)){
            throw new Exception("服务name已经存在");
        }
    }
}
