package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.ResourceMapper;
import com.jiuzhou.bootwork.dao.model.Resource;
import com.jiuzhou.bootwork.dao.model.ResourceExample;
import com.jiuzhou.bootwork.dao.model.ResourceKey;
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
    public Long insert(ResourceDto resourceDto) throws Exception {
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
     * @throws Exception
     */
    private void validateInsert(ResourceDto resourceDto) throws Exception {
        String name = resourceDto.getName();
        String url = resourceDto.getUrl();
        String type = resourceDto.getType();
        Long serverId = resourceDto.getServerId();

        if (StringUtils.isEmpty(name)){
            throw new Exception("资源名称为空");
        }
        if (StringUtils.isEmpty(url)){
            throw new Exception("资源url为空");
        }
        if (serverId == null || serverId.equals(0L)){
            throw new Exception("serverId为空");
        }
        if (StringUtils.isEmpty(type)){
            throw new Exception("资源请求方式为空");
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
            throw new Exception("请求类型错误");
        }

        validateName(name);

        validateServerId(serverId);

        validateUrlType(url, type, serverId);
    }

    private void validateServerId(Long serverId) throws Exception {
        ServerDto serverDto = serverService.selectByPrimaryKey(serverId);
        if (serverDto == null){
            throw new Exception("serverId不存在");
        }
    }

    /**
     * 校验URL和type、serverId是否是已经存在
     * @param url
     * @param type
     */
    private void validateUrlType(String url, String type, Long serverId) throws Exception {
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andUrlEqualTo(url);
        criteria.andTypeEqualTo(type);
        criteria.andServerIdEqualTo(serverId);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if (!CollectionUtils.isEmpty(resources)){
            throw new Exception("资源已经存在");
        }
    }

    /**
     * 校验资源名字是否已经存在
     */
    private void validateName(String name) throws Exception {
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if (!CollectionUtils.isEmpty(resources)){
            throw new Exception("资源名称重复");
        }
    }

    @Override
    public ResourceDto selectOneByName(String name) throws Exception {
        if (StringUtils.isEmpty(name)){
            return null;
        }
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        return dealList(resources);
    }

    private ResourceDto dealList(List resources) throws Exception {
        if (CollectionUtils.isEmpty(resources)){
            return null;
        }else if (resources.size() == 1){
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(resources.get(0), resourceDto);
            return resourceDto;
        }else {
            throw new Exception("查询结果为多条");
        }
    }

    @Override
    public ResourceDto selectOneByUrl(String url) throws Exception {
        if (StringUtils.isEmpty(url)){
            return null;
        }
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andUrlEqualTo(url);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        return dealList(resources);
    }

    @Override
    public boolean updateById(ResourceDto resourceDto, Long id) throws Exception {
        ResourceDto dto = selectById(id);
        if (dto == null){
            throw new Exception("资源不存在");
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
    public ResourceDto selectById(Long id) throws Exception {
        if (id == null || id.equals(0L)){
            throw new Exception("ID为空");
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
}
