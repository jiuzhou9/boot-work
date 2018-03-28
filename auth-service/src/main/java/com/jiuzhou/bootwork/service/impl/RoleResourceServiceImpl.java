package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.RoleResourceMapper;
import com.jiuzhou.bootwork.dao.model.RoleResource;
import com.jiuzhou.bootwork.dao.model.RoleResourceExample;
import com.jiuzhou.bootwork.dao.model.RoleResourceKey;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.RoleResourceService;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.RoleResourceDto;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2018/03/26
 */
@Service
@Slf4j
public class RoleResourceServiceImpl implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(RoleResourceDto roleResourceDto) throws Exception {
        validateInsert(roleResourceDto);
        RoleResource roleResource = new RoleResource();
        BeanUtils.copyProperties(roleResourceDto, roleResource);
        int i = roleResourceMapper.insertSelective(roleResource);
        return roleResource.getId();
    }

    private void validateInsert(RoleResourceDto roleResourceDto) throws Exception {
        Long resourceId = roleResourceDto.getResourceId();
        Long roleId = roleResourceDto.getRoleId();
        if (resourceId == null || resourceId.equals(0L)){
            throw new Exception("资源ID为空");
        }
        if (roleId == null || roleId.equals(0L)){
            throw new Exception("角色ID为空");
        }

        RoleDto roleDto = roleService.selectById(roleId);
        if (roleDto == null){
            throw new Exception("角色ID不存在");
        }
        ResourceDto resourceDto = resourceService.selectById(resourceId);
        if (resourceDto == null){
            throw new Exception("资源ID不存在");
        }

        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andResourceIdEqualTo(resourceId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        if (!CollectionUtils.isEmpty(roleResources)){
            throw new Exception("该角色资源映射关系已经存在");
        }
    }

    @Override
    public List<ResourceDto> selectByRoleName(String roleName) throws Exception {
        if (StringUtils.isEmpty(roleName)){
            throw new Exception("角色名称为空");
        }

        RoleDto roleDto = roleService.selectOneByName(roleName);
        if (roleDto == null){
            throw new Exception("该角色名称信息不存在");
        }
        Long roleId = roleDto.getId();

        return selectResourceByRoleId(roleId);
    }

    @Override
    public List<ResourceDto> selectResourceByRoleId(Long roleId) throws Exception {
        List<RoleResourceDto> roleResourceDtos = selectRoleResourceByRoleId(roleId);

        List<Long> resourceIds = new ArrayList<>();
        roleResourceDtos.forEach( roleResourceDto -> {
            Long resourceId = roleResourceDto.getResourceId();
            resourceIds.add(resourceId);
        });

        List<ResourceDto> dtos = resourceService.selectByIds(resourceIds);
        return dtos;
    }

    private List<RoleResourceDto> selectRoleResourceByRoleId(Long roleId){
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);

        if (CollectionUtils.isEmpty(roleResources)){
            return null;
        }

        List<RoleResourceDto> roleResourceDtos = new ArrayList<>();
        roleResources.forEach( roleResource -> {
            RoleResourceDto roleResourceDto = new RoleResourceDto();
            BeanUtils.copyProperties(roleResource, roleResourceDto);
            roleResourceDtos.add(roleResourceDto);
        });
        return roleResourceDtos;
    }

    @Override
    public List<RoleDto> selectRolesByResourceId(Long resourceId) throws Exception {
        List<RoleResourceDto> roleResourceDtos = selectRoleResourceByResourceId(resourceId);

        List<Long> roleIds = new ArrayList<>();
        roleResourceDtos.forEach( roleResourceDto -> {
            roleIds.add(roleResourceDto.getId());
        });

        List<RoleDto> roleDtos = roleService.selectByIds(roleIds);
        return roleDtos;
    }

    private List<RoleResourceDto> selectRoleResourceByResourceId(Long resourceId){
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andResourceIdEqualTo(resourceId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        List<RoleResourceDto> roleResourceDtos = new ArrayList<>();
        roleResources.forEach( roleResource -> {
            RoleResourceDto roleResourceDto = new RoleResourceDto();
            BeanUtils.copyProperties(roleResource, roleResourceDto);
            roleResourceDtos.add(roleResourceDto);
        });
        return roleResourceDtos;
    }

    @Override
    public List<RoleDto> selectRolesByResourceName(String resourceName) throws Exception {
        ResourceDto resourceDto = resourceService.selectOneByName(resourceName);
        Long resourceId = resourceDto.getId();
        return selectRolesByResourceId(resourceId);
    }

    @Override
    public List<RoleDto> selectRolesByResourceUrl(String url) throws Exception {
        ResourceDto resourceDto = resourceService.selectOneByUrl(url);
        Long resourceId = resourceDto.getId();
        List<RoleDto> roleDtos = selectRolesByResourceId(resourceId);
        return roleDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(RoleResourceDto roleResourceDto) throws Exception {
        validateUpdate(roleResourceDto);
        RoleResource roleResource = new RoleResource();
        BeanUtils.copyProperties(roleResourceDto, roleResource);
        int i = roleResourceMapper.updateByPrimaryKeySelective(roleResource);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(RoleResourceDto roleResourceDto) throws Exception {
        if (roleResourceDto == null){
            throw new Exception("RoleResource信息为空");
        }
        Long id = roleResourceDto.getId();
        if (id == null || id.equals(0L)){
            throw new Exception("id为空");
        }

        RoleResourceKey roleResourceKey = new RoleResourceKey();
        roleResourceKey.setId(id);
        RoleResource roleResource = roleResourceMapper.selectByPrimaryKey(roleResourceKey);
        if (roleResource == null){
            throw new Exception("id不存在");
        }

        Long resourceId = roleResourceDto.getResourceId();
        Long roleId = roleResourceDto.getRoleId();
        if (resourceId != null && resourceId.equals(0L)){
            throw new Exception("资源ID不能为0");
        }
        if (roleId != null && roleId.equals(0L)){
            throw new Exception("角色ID不能为0");
        }

        if (resourceId != null){
            ResourceDto resourceDto = resourceService.selectById(resourceId);
            if (resourceDto == null){
                throw new Exception("资源不存在");
            }
        }
        if (roleId != null){
            RoleDto roleDto = roleService.selectById(roleId);
            if (roleDto == null){
                throw new Exception("角色不存在");
            }
        }

        if (resourceId != null && roleId == null){
            RoleResource roleResourceResult = selectRoleResource(roleResource.getRoleId(), resourceId);
            if (roleResourceResult != null){
                throw new Exception("该映射已存在");
            }
        }

        if (resourceId == null && roleId != null){
            RoleResource roleResourceResult = selectRoleResource(roleId, roleResource.getResourceId());
            if (roleResourceResult != null){
                throw new Exception("该映射已存在");
            }
        }

        if (resourceId != null && roleId != null){
            RoleResource roleResourceResult = selectRoleResource(roleId, resourceId);
            if (roleResourceResult != null){
                throw new Exception("该映射已存在");
            }
        }

    }

    private RoleResource selectRoleResource(Long roleId, Long resourceId) throws Exception {
        if (roleId == null){
            throw new Exception("RoleID为空");
        }
        if (resourceId == null){
            throw new Exception("ResourceID为空");
        }
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andResourceIdEqualTo(resourceId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        if (CollectionUtils.isEmpty(roleResources)){
            return null;
        }
        if (roleResources.size() == 1){
            return roleResources.get(0);
        }else {
            throw new Exception("查询到多条数据");
        }
    }
}