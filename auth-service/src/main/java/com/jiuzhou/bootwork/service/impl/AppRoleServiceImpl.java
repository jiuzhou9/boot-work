package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.AppRoleMapper;
import com.jiuzhou.bootwork.dao.model.AppRole;
import com.jiuzhou.bootwork.dao.model.AppRoleExample;
import com.jiuzhou.bootwork.service.AppRoleService;
import com.jiuzhou.bootwork.service.AppService;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.dto.AppDto;
import com.jiuzhou.bootwork.service.dto.AppRoleDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
@Slf4j
@Service
public class AppRoleServiceImpl implements AppRoleService {

    @Autowired
    private AppRoleMapper appRoleMapper;

    @Autowired
    private AppService appService;

    @Autowired
    private RoleService roleService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(AppRoleDto appRoleDto) throws Exception {
        validateInsert(appRoleDto);
        AppRole appRole = new AppRole();
        BeanUtils.copyProperties(appRoleDto, appRole);
        int i = appRoleMapper.insertSelective(appRole);
        return appRole.getId();
    }

    private void validateInsert(AppRoleDto appRoleDto) throws Exception {
        if (appRoleDto == null){
            throw new Exception("app 角色信息为空");
        }
        Long appId = appRoleDto.getAppId();
        Long roleId = appRoleDto.getRoleId();
        if (appId == null || appId.equals(0L)){
            throw new Exception("APPID为空");
        }
        if (roleId == null || roleId.equals(0L)){
            throw new Exception("角色ID为空");
        }

        AppDto appDto = appService.selectById(appId);
        if (appDto == null){
            throw new Exception("APPID信息不存在");
        }

        RoleDto roleDto = roleService.selectById(roleId);
        if (roleDto == null){
            throw new Exception("roleID信息不存在");
        }

        AppRoleDto dto = selectOneByAppIdRoleId(appId, roleId);
        if (dto != null){
            throw new Exception("该APP角色映射关系已经存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(AppRoleDto appRoleDto) throws Exception {
        return false;
    }

    @Override
    public List<AppRoleDto> selectByAppId(Long appId) throws Exception {
        return null;
    }

    @Override
    public AppRoleDto selectById(Long id) throws Exception {
        return null;
    }

    @Override
    public AppRoleDto selectOneByAppIdRoleId(Long appId, Long roleId) throws Exception {
        if (appId == null || appId.equals(0L)){
            throw new Exception("APPID信息为空");
        }
        if (roleId == null || roleId.equals(0L)){
            throw new Exception("角色ID信息为空");
        }

        AppRoleExample appRoleExample = new AppRoleExample();
        AppRoleExample.Criteria criteria = appRoleExample.createCriteria();
        criteria.andAppIdEqualTo(appId);
        criteria.andRoleIdEqualTo(roleId);
        List<AppRole> appRoles = appRoleMapper.selectByExample(appRoleExample);
        if (CollectionUtils.isEmpty(appRoles)){
            return null;
        }else if (appRoles.size() == 1){
            AppRoleDto appRoleDto = new AppRoleDto();
            BeanUtils.copyProperties(appRoles.get(0), appRoleDto);
            return appRoleDto;
        }else {
            throw new Exception("查询到多条数据");
        }
    }
}
