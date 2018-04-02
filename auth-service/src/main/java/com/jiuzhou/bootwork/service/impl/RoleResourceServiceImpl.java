package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.RoleResourceMapper;
import com.jiuzhou.bootwork.dao.model.RoleResource;
import com.jiuzhou.bootwork.dao.model.RoleResourceExample;
import com.jiuzhou.bootwork.dao.model.RoleResourceKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.RoleResourceService;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.ServerService;
import com.jiuzhou.bootwork.service.dto.PermissionDto;
import com.jiuzhou.bootwork.service.dto.ResourceDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.RoleResourceDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ServerService serverService;

    @Autowired
    private RoleResourceService roleResourceService;

    private HashMap<String, Collection<String>> map = null;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(RoleResourceDto roleResourceDto) throws ServiceException {
        validateInsert(roleResourceDto);
        RoleResource roleResource = new RoleResource();
        BeanUtils.copyProperties(roleResourceDto, roleResource);
        int i = roleResourceMapper.insertSelective(roleResource);
        return roleResource.getId();
    }

    private void validateInsert(RoleResourceDto roleResourceDto) throws ServiceException {
        Long resourceId = roleResourceDto.getResourceId();
        Long roleId = roleResourceDto.getRoleId();
        if (resourceId == null || resourceId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_EMPTY);
        }
        if (roleId == null || roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_EMPTY);
        }
        RoleDto roleDto = roleService.selectById(roleId);
        if (roleDto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }
        ResourceDto resourceDto = resourceService.selectById(resourceId);
        if (resourceDto == null){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_NOT_EXIST);
        }

        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andResourceIdEqualTo(resourceId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        if (!CollectionUtils.isEmpty(roleResources)){
            throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_HAS_ALREADY_EXISTED);
        }
    }

    @Override
    public List<ResourceDto> selectByRoleName(String roleName) throws ServiceException {
        if (StringUtils.isEmpty(roleName)){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_IS_EMPTY);
        }

        RoleDto roleDto = roleService.selectOneByName(roleName);
        if (roleDto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_IS_NOT_EXIST);
        }
        Long roleId = roleDto.getId();

        return selectResourceByRoleId(roleId);
    }

    @Override
    public List<ResourceDto> selectResourceByRoleId(Long roleId) throws ServiceException {
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
    public List<RoleDto> selectAvailableRolesByResourceId(Long resourceId) throws ServiceException {
        List<RoleResourceDto> roleResourceDtos = selectAvailableRoleResourceByResourceId(resourceId);

        List<Long> roleIds = new ArrayList<>();
        roleResourceDtos.forEach( roleResourceDto -> {
            roleIds.add(roleResourceDto.getId());
        });

        List<RoleDto> roleDtos = roleService.selectAvailableByIds(roleIds);
        return roleDtos;
    }

    private List<RoleResourceDto> selectAvailableRoleResourceByResourceId(Long resourceId){
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andResourceIdEqualTo(resourceId);
        criteria.andAvailableEqualTo(true);
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
    public List<RoleDto> selectRolesByResourceName(String resourceName) throws ServiceException {
        ResourceDto resourceDto = resourceService.selectOneByName(resourceName);
        Long resourceId = resourceDto.getId();
        return selectAvailableRolesByResourceId(resourceId);
    }

    @Override
    public List<RoleDto> selectRolesByResourceUrl(String url) throws ServiceException {
        ResourceDto resourceDto = resourceService.selectOneByUrl(url);
        Long resourceId = resourceDto.getId();
        List<RoleDto> roleDtos = selectAvailableRolesByResourceId(resourceId);
        return roleDtos;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(RoleResourceDto roleResourceDto) throws ServiceException {
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

    private void validateUpdate(RoleResourceDto roleResourceDto) throws ServiceException {
        if (roleResourceDto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_PARAMETER_IS_EMPTY);
        }
        Long id = roleResourceDto.getId();
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_ID_PARAMETER_IS_EMPTY);
        }

        RoleResourceKey roleResourceKey = new RoleResourceKey();
        roleResourceKey.setId(id);
        RoleResource roleResource = roleResourceMapper.selectByPrimaryKey(roleResourceKey);
        if (roleResource == null){
            throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_ID_IS_NOT_EXIST);
        }

        Long resourceId = roleResourceDto.getResourceId();
        Long roleId = roleResourceDto.getRoleId();
        if (resourceId != null && resourceId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_EMPTY);
        }
        if (roleId != null && roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_EMPTY);
        }

        if (resourceId != null){
            ResourceDto resourceDto = resourceService.selectById(resourceId);
            if (resourceDto == null){
                throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_NOT_EXIST);
            }
        }
        if (roleId != null){
            RoleDto roleDto = roleService.selectById(roleId);
            if (roleDto == null){
                throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
            }
        }

        if (resourceId != null && roleId == null){
            RoleResource roleResourceResult = selectRoleResource(roleResource.getRoleId(), resourceId);
            if (roleResourceResult != null){
                throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_HAS_ALREADY_EXISTED);
            }
        }

        if (resourceId == null && roleId != null){
            RoleResource roleResourceResult = selectRoleResource(roleId, roleResource.getResourceId());
            if (roleResourceResult != null){
                throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_HAS_ALREADY_EXISTED);
            }
        }

        if (resourceId != null && roleId != null){
            RoleResource roleResourceResult = selectRoleResource(roleId, resourceId);
            if (roleResourceResult != null){
                throw new ServiceException(HttpErrorEnum.ROLE_RESOURCE_HAS_ALREADY_EXISTED);
            }
        }

    }

    private RoleResource selectRoleResource(Long roleId, Long resourceId) throws ServiceException {
        if (roleId == null || roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_EMPTY);
        }
        if (resourceId == null || resourceId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.RESOURCE_ID_IS_EMPTY);
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
            throw new ServiceException(HttpErrorEnum.ROLE_ID_RESOURCE_ID_QUERY_MANY_RESULTS);
        }
    }

    @Override
    public void loadPermission(){
        //查询server列表（true）
        List<ServerDto> serverDtos = serverService.selectAvailable();
        if (CollectionUtils.isEmpty(serverDtos)){
            return;
        }
        //查询server Resource列表（true）
        List<ResourceDto> resourceDtos = resourceService.selectAvailable();
        if (CollectionUtils.isEmpty(resourceDtos)){
            return;
        }

        //查询有效的role
        List<RoleDto> roleDtos = roleService.selectAvilable();
        if (CollectionUtils.isEmpty(roleDtos)){
            return;
        }

        List<RoleResourceDto> roleResourceDtos = roleResourceService.selectAvailable();
        if (CollectionUtils.isEmpty(roleResourceDtos)){
            return;
        }
        List<PermissionDto> permissionDtos = dealPermission(serverDtos, resourceDtos, roleDtos, roleResourceDtos);
        map = new HashMap<>();

        permissionDtos.forEach( permissionDto -> {
            map.put(permissionDto.getServerResource() + "," + permissionDto.getMethodType(), permissionDto.getRoleNames());
        });
    }

    /**
     * 构建Permission集合
     * @param serverDtos
     * @param resourceDtos
     * @param roleDtos
     * @param roleResourceDtos
     * @return
     */
    private List<PermissionDto> dealPermission(List<ServerDto> serverDtos, List<ResourceDto> resourceDtos,
                                               List<RoleDto> roleDtos, List<RoleResourceDto> roleResourceDtos) {

        List<PermissionDto> permissionDtos = buildPermission(serverDtos, resourceDtos);

        Map<Long, List<String>> resourceIdRoleNameMap = buildResourceIdRoleNameMap(roleDtos, roleResourceDtos);

        buildPermission(permissionDtos, resourceIdRoleNameMap);

        return permissionDtos;
    }

    /**
     * 构建Permission集合
     * @param permissionDtos
     * @param resourceIdRoleNameMap
     * @return
     */
    private List<PermissionDto> buildPermission(List<PermissionDto> permissionDtos, Map<Long, List<String>> resourceIdRoleNameMap){
        Map<Long, PermissionDto> permissionMap = new HashMap<>();
        permissionDtos.forEach( permissionDto -> {
            permissionMap.put(permissionDto.getResourceId(), permissionDto);
        });
        permissionDtos.clear();
//        List<PermissionDto> permissions = new ArrayList<>();
        permissionMap.forEach((resourceId, permissionDto) -> {
            List<String> list = resourceIdRoleNameMap.get(resourceId);
            permissionDto.setRoleNames(list);
            permissionDtos.add(permissionDto);
        });
        return permissionDtos;
    }

    /**
     * 构建map key resourceId value roleName
     * @param roleDtos
     * @param roleResourceDtos
     * @return
     */
    private Map<Long, List<String>> buildResourceIdRoleNameMap(List<RoleDto> roleDtos, List<RoleResourceDto> roleResourceDtos){
        Map<Long, RoleDto> roleDtoMap = new HashMap<>();
        roleDtos.forEach( roleDto -> {
            roleDtoMap.put(roleDto.getId(), roleDto);
        });

        Map<Long, List<String>> resourceIdRoleNameMap = new HashMap<>();
        List<String> roleNames;
        for (RoleResourceDto roleResourceDto : roleResourceDtos) {
            Long resourceId = roleResourceDto.getResourceId();
            Long roleId = roleResourceDto.getRoleId();
            RoleDto roleDto = roleDtoMap.get(roleId);
            String roleName = roleDto.getName();
            if (resourceIdRoleNameMap.containsKey(resourceId)){
                List<String> list = resourceIdRoleNameMap.get(resourceId);
                list.add(roleName);
            }else {
                roleNames = new ArrayList<>();
                roleNames.add(roleName);
                resourceIdRoleNameMap.put(resourceId, roleNames);
            }
        }
        return resourceIdRoleNameMap;
    }


    /**
     * 构建Permission ResourceId serverResource
     * @param serverDtos
     * @param resourceDtos
     * @return
     */
    private List<PermissionDto> buildPermission(List<ServerDto> serverDtos, List<ResourceDto> resourceDtos){
        List<PermissionDto> permissionDtos = new ArrayList<>();
        Map<Long, ServerDto> serverDtoMap = new HashMap<>();
        serverDtos.forEach( serverDto -> {
            serverDtoMap.put(serverDto.getId(), serverDto);
        });

        resourceDtos.forEach( resourceDto -> {
            Long serverId = resourceDto.getServerId();
            ServerDto serverDto = serverDtoMap.get(serverId);
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setServerResource(serverDto.getName() + resourceDto.getUrl());
            permissionDto.setResourceId(resourceDto.getId());
            permissionDto.setMethodType(resourceDto.getType());
            permissionDtos.add(permissionDto);
        });
        return permissionDtos;
    }

    @Override
    public List<RoleResourceDto> selectAvailable() {
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        RoleResourceExample.Criteria criteria = roleResourceExample.createCriteria();
        criteria.andAvailableEqualTo(true);
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
    public Collection<String> getAttributes(String serverResource, String method) {
        Collection<String> attributesCompletelyEquals = new ArrayList<>();
        String[] split = serverResource.split("/");
        for (int count = 0; count < split.length - 1; count++){
            attributesCompletelyEquals = getAttributesCompletelyEquals(serverResource, method, count);
            if (!CollectionUtils.isEmpty(attributesCompletelyEquals)){
                return attributesCompletelyEquals;
            }
        }
        return attributesCompletelyEquals;
    }



    /**
     * 完全匹配
     * @param serverResource
     * @param method
     * @return
     */
    private Collection<String> getAttributesCompletelyEquals(String serverResource, String method, int count){
        //去掉参数的请求URL
        String[] split = serverResource.split("/");
        serverResource = "";
        for (int i = 0; i < split.length - count; i++) {
            if (i != 0){
                serverResource = serverResource + "/" + split[i];
            }
        }


        //加载权限列表
        String serverResourceMethod = serverResource + "," + method;
        if (map == null || map.size() == 0) {
            loadPermission();
        }

        String resUrl;
        String resUrlOrigin;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            resUrlOrigin = resUrl;
            if (count > 0){
                //因为请求URL进行了去参数，那么数据库中的资源也进行去参数
                String[] resourceArray = resUrl.split(",");
                String resource = resourceArray[0];
                String[] resUrlSplit = resource.split("/");
                resource = "";
                for (int i = 0; i < resUrlSplit.length - count; i++) {
                    if (i != 0){
                        resource = resource + "/" + resUrlSplit[i];
                    }
                }
                resUrl = resource + "," + resourceArray[1];
            }
            if (resUrl.equals(serverResourceMethod)) {
                return map.get(resUrlOrigin);
            }
        }
        //TODO 此处将来可以根据需求改进，如果数据库里没有资源角色映射的话，那么就抛异常不进行放行,现在是数据库中没有资源角色映射那么说明这个资源是不需要权限控制的
        return null;
    }



    @Override
    public boolean decide(String username, String resourcePath, String method) {
        return false;
    }

    @Override
    public boolean decide(String userName, String resourcePath, String appName, String method) {
        return false;
    }
}
