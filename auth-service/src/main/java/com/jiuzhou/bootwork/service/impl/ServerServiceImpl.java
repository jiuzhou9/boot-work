package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.ServerMapper;
import com.jiuzhou.bootwork.dao.model.Server;
import com.jiuzhou.bootwork.dao.model.ServerExample;
import com.jiuzhou.bootwork.dao.model.ServerKey;
import com.jiuzhou.bootwork.excep.ServiceException;
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

import java.util.ArrayList;
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
    public Long insert(ServerDto serverDto) throws ServiceException {
        Server server = new Server();
        BeanUtils.copyProperties(serverDto, server);
        validateInsert(serverDto);
        int insert = serverMapper.insertSelective(server);
        return server.getId();
    }

    /**
     * 插入规则校验
     * @param serverDto
     * @throws ServiceException
     */
    private void validateInsert(ServerDto serverDto) throws ServiceException {
        if (serverDto == null){
            throw new ServiceException("服务信息为空");
        }
        String name = serverDto.getName();
        if (StringUtils.isEmpty(name)){
            throw new ServiceException("服务name为空");
        }
        String description = serverDto.getDescription();
        if (StringUtils.isEmpty(description)){
            throw new ServiceException("服务描述为空");
        }

        ServerExample serverExample = new ServerExample();
        ServerExample.Criteria criteria = serverExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Server> servers = serverMapper.selectByExample(serverExample);
        if (!CollectionUtils.isEmpty(servers)){
            throw new ServiceException("服务name已经存在");
        }
    }

    @Override
    public List<ServerDto> selectByName(String name) {
        ServerExample serverExample = new ServerExample();
        ServerExample.Criteria criteria = serverExample.createCriteria();
        criteria.andNameLike(name);
        List<Server> servers = serverMapper.selectByExample(serverExample);
        List<ServerDto> serverDtos = new ArrayList<>();
        servers.forEach( server -> {
            ServerDto serverDto = new ServerDto();
            BeanUtils.copyProperties(server, serverDto);
            serverDtos.add(serverDto);
        });
        return serverDtos;
    }

    @Override
    public ServerDto selectOne(String name) throws ServiceException {
        ServerExample serverExample = new ServerExample();
        ServerExample.Criteria criteria = serverExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Server> servers = serverMapper.selectByExample(serverExample);
        if (CollectionUtils.isEmpty(servers)){
            return null;
        }else if (servers.size() > 1){
            throw new ServiceException("该name存在多条数据");
        }else {
            Server server = servers.get(0);
            ServerDto serverDto = new ServerDto();
            BeanUtils.copyProperties(server, serverDto);
            return serverDto;
        }
    }

    @Override
    public ServerDto selectByPrimaryKey(Long key) throws ServiceException {
        if (key == null || key.equals(0L)){
            throw new ServiceException("ID为空");
        }
        ServerKey serverKey = new ServerKey();
        serverKey.setId(key);
        Server server = serverMapper.selectByPrimaryKey(serverKey);
        if (server == null){
            return null;
        }
        ServerDto serverDto = new ServerDto();
        BeanUtils.copyProperties(server, serverDto);
        return serverDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateByKey(ServerDto serverDto) throws ServiceException {
        validateUpdate(serverDto);
        Server server = new Server();
        BeanUtils.copyProperties(serverDto, server);
        int i = serverMapper.updateByPrimaryKeySelective(server);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(ServerDto serverDto) throws ServiceException {
        if (serverDto == null){
            throw new ServiceException("server信息为空");
        }
        Long id = serverDto.getId();
        if (id == null){
            throw new ServiceException("id为空");
        }
        if (id.equals(0L)){
            throw new ServiceException("ID为零");
        }
    }
}
