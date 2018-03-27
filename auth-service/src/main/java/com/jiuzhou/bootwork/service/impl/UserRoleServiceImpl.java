package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.UserRoleMapper;
import com.jiuzhou.bootwork.dao.model.UserRole;
import com.jiuzhou.bootwork.dao.model.UserRoleExample;
import com.jiuzhou.bootwork.dao.model.UserRoleKey;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.UserRoleService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.RoleDto;
import com.jiuzhou.bootwork.service.dto.UserDto;
import com.jiuzhou.bootwork.service.dto.UserRoleDto;
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
 * @date 2018/03/27
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(UserRoleDto userRoleDto) throws Exception {
        validateInsert(userRoleDto);
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userRoleDto, userRole);
        int i = userRoleMapper.insertSelective(userRole);
        return userRole.getId();
    }

    private void validateInsert(UserRoleDto userRoleDto) throws Exception {
        if (userRoleDto == null){
            throw new Exception("用户角色信息为空");
        }

        Long roleId = userRoleDto.getRoleId();
        Long userId = userRoleDto.getUserId();
        if (roleId == null || roleId.equals(0L)){
            throw new Exception("角色ID为空");
        }
        if (userId == null || userId.equals(0L)){
            throw new Exception("用户ID为空");
        }

        UserRoleDto dto = null;
        dto = selectByUserIdRoleId(userId, roleId);
        if (dto != null){
            throw new Exception("该用户角色映射已经存在");
        }

    }

    @Override
    public List<RoleDto> selectOneByUsername(String username) throws Exception {
        if (StringUtils.isEmpty(username)){
            throw new Exception("用户名为空");
        }

        UserDto userDto = userService.selectOneByUsername(username);
        if (userDto == null){
            return null;
        }
        return selectByUserId(userDto.getId());
    }

    @Override
    public boolean updateById(UserRoleDto userRoleDto) throws Exception {
        validateUpdate(userRoleDto);
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userRoleDto, userRole);
        int i = userRoleMapper.updateByPrimaryKeySelective(userRole);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(UserRoleDto userRoleDto) throws Exception {
        if (userRoleDto == null){
            throw new Exception("用户角色信息为空");
        }
        Long id = userRoleDto.getId();
        if (id == null || id.equals(0L)){
            throw new Exception("用户角色ID为空");
        }
        UserRoleDto dto = selectById(id);
        if (dto == null){
            throw new Exception("ID不存在");
        }
        Long roleId = userRoleDto.getRoleId();
        Long userId = userRoleDto.getUserId();
        if (roleId != null && roleId.equals(0L)){
            throw new Exception("角色ID为0");
        }
        if (userId != null && userId.equals(0L)){
            throw new Exception("用户ID为0");
        }
        if (userId != null){
            UserDto userDto = userService.selectById(userId);
            if (userDto == null){
                throw new Exception("用户ID不存在");
            }
        }
        if (roleId != null){
            RoleDto roleDto = roleService.selectById(roleId);
            if (roleDto == null){
                throw new Exception("角色ID不存在");
            }
        }
        if (roleId == null && userId != null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(userId, dto.getRoleId());
            if (userRoleDtoResult != null){
                throw new Exception("该用户角色映射已经存在");
            }
        }
        if (roleId != null && userId == null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(dto.getUserId(), roleId);
            if (userRoleDtoResult != null){
                throw new Exception("该用户角色映射已经存在");
            }
        }
        if (roleId != null && userId != null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(dto.getUserId(), dto.getRoleId());
            if (userRoleDtoResult != null){
                throw new Exception("该用户角色映射已经存在");
            }
        }
    }

    @Override
    public List<RoleDto> selectOneByMobile(String mobile) throws Exception {
        if (StringUtils.isEmpty(mobile)){
            throw new Exception("手机号码为空");
        }
        UserDto userDto = userService.selectOneByMobile(mobile);
        if (userDto == null){
            return null;
        }

        Long userId = userDto.getId();
        return selectByUserId(userId);
    }

    @Override
    public List<RoleDto> selectByUserId(Long userId) throws Exception {
        if (userId == null || userId.equals(0L)){
            throw new Exception("userID为空");
        }
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)){
            return null;
        }

        List<Long> roleIds = new ArrayList<>();
        userRoles.forEach( userRole -> {
            Long roleId = userRole.getRoleId();
            roleIds.add(roleId);
        });

        List<RoleDto> roleDtos = roleService.selectByIds(roleIds);
        return roleDtos;
    }

    @Override
    public UserRoleDto selectByUserIdRoleId(Long userId, Long roleId) throws Exception {
        if (userId == null || userId.equals(0L)){
            throw new Exception("用户ID为空");
        }
        if (userId == null || userId.equals(0L)){
            throw new Exception("用户ID为空");
        }
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andRoleIdEqualTo(roleId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)){
            return null;
        }else if (userRoles.size() == 1){
            UserRoleDto userRoleDto = new UserRoleDto();
            BeanUtils.copyProperties(userRoles.get(0), userRoleDto);
            return userRoleDto;
        }else {
            throw new Exception("查询到多条数据");
        }
    }

    @Override
    public UserRoleDto selectById(Long id) throws Exception {
        if (id == null || id.equals(0L)){
            throw new Exception("id为空");
        }
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setId(id);
        UserRole userRole = userRoleMapper.selectByPrimaryKey(userRoleKey);
        if (userRole == null){
            return null;
        }
        UserRoleDto userRoleDto = new UserRoleDto();
        BeanUtils.copyProperties(userRole, userRoleDto);
        return userRoleDto;
    }
}
