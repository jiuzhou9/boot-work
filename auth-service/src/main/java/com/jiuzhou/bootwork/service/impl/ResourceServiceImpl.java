package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.ResourceMapper;
import com.jiuzhou.bootwork.dao.model.Resource;
import com.jiuzhou.bootwork.dao.model.ResourceExample;
import com.jiuzhou.bootwork.dao.model.ResourceKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.ServerService;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import com.jiuzhou.bootwork.service.dto.ServerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/23
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ServerService serverService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(ResourceDto resourceDto) throws ServiceException {
        if (resourceDto == null){
            return null;
        }

        validateInsert(resourceDto);

        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        int i = resourceMapper.insertSelective(resource);
        return resource.getId();
    }

    /**
     * 校验resource是否可以插入
     * @param resourceDto
     * @throws ServiceException
     */
    private void validateInsert(ResourceDto resourceDto) throws ServiceException {
        String name = resourceDto.getName();
        String url = resourceDto.getUrl();
        String type = resourceDto.getType();
        Long serverId = resourceDto.getServerId();

        if (StringUtils.isEmpty(name)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_NAME_IS_EMPTY);
        }
        if (StringUtils.isEmpty(url)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_URL_PARAMETER_IS_EMPTY);
        }
        if (serverId == null || serverId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.SERVER_ID_PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isEmpty(type)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_TYPE_PARAMETER_IS_EMPTY);
        }else if (type != RequestMethod.GET.name()
                        &&
                        type != RequestMethod.DELETE.name()
                        &&
                        type != RequestMethod.HEAD.name()
                        &&
                        type != RequestMethod.OPTIONS.name()
                        &&
                        type != RequestMethod.PATCH.name()
                        &&
                        type != RequestMethod.POST.name()
                        &&
                        type != RequestMethod.PUT.name()
                        &&
                        type != RequestMethod.TRACE.name()){
            throw new ServiceException(HttpErrorEnum.RESOURCE_TYPE_PARAMETER_IS_ERROR);
        }

        validateName(name);

        validateServerId(serverId);

        validateUrlType(url, type, serverId);
    }

    private void validateServerId(Long serverId) throws ServiceException {
        ServerDto serverDto = serverService.selectByPrimaryKey(serverId);
        if (serverDto == null){
            throw new ServiceException(HttpErrorEnum.SERVER_ID_IS_NOT_EXIST);
        }
    }

    /**
     * 校验URL和type、serverId是否是已经存在
     * @param url
     * @param type
     */
    private void validateUrlType(String url, String type, Long serverId) throws ServiceException {
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andUrlEqualTo(url);
        criteria.andTypeEqualTo(type);
        criteria.andServerIdEqualTo(serverId);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if (!CollectionUtils.isEmpty(resources)){
            throw new ServiceException(HttpErrorEnum.SERVER_HAS_ALREADY_EXISTED);
        }
    }

    /**
     * 校验资源名字是否已经存在
     */
    private void validateName(String name) throws ServiceException {
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if (!CollectionUtils.isEmpty(resources)){
            throw new ServiceException(HttpErrorEnum.SERVER_NAME_HAS_ALREADY_EXISTED);
        }
    }

    @Override
    public ResourceDto selectOneByName(String name) throws ServiceException {
        if (StringUtils.isEmpty(name)){
            return null;
        }
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        return dealList(resources);
    }

    private ResourceDto dealList(List resources) throws ServiceException {
        if (CollectionUtils.isEmpty(resources)){
            return null;
        }else if (resources.size() == 1){
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resources.get(0), resourceDto);
            return resourceDto;
        }else {
            throw new ServiceException(HttpErrorEnum.RESOURCE_URL_PARAMETER_QUERY_MANY_RESULTS);
        }
    }

    @Override
    public ResourceDto selectOneByUrl(String url) throws ServiceException {
        if (StringUtils.isEmpty(url)){
            return null;
        }
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andUrlEqualTo(url);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        return dealList(resources);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(ResourceDto resourceDto, Long id) throws ServiceException {
        ResourceDto dto = selectById(id);
        if (dto == null){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_NOT_EXIST);
        }
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        resource.setId(id);
        int i = resourceMapper.updateByPrimaryKeySelective(resource);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ResourceDto selectById(Long id) throws ServiceException {
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_PARAMETER_IS_EMPTY);
        }

        ResourceKey resourceKey = new ResourceKey();
        resourceKey.setId(id);
        Resource resource = resourceMapper.selectByPrimaryKey(resourceKey);
        if (resource == null){
            return null;
        }
        ResourceDto resourceDto = new ResourceDto();
        BeanUtils.copyProperties(resource, resourceDto);
        return resourceDto;
    }

    @Override
    public List<ResourceDto> selectByIds(List<Long> ids) throws ServiceException {
        if (CollectionUtils.isEmpty(ids)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_PARAMETER_IS_EMPTY);
        }
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andIdIn(ids);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        List<ResourceDto> dtos = new ArrayList<>();
        resources.forEach( resource -> {
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resource, resourceDto);
            dtos.add(resourceDto);
        });
        return dtos;
    }
}
