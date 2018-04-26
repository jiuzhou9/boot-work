package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.UserRoleMapper;
import com.jiuzhou.bootwork.dao.model.UserRole;
import com.jiuzhou.bootwork.dao.model.UserRoleExample;
import com.jiuzhou.bootwork.dao.model.UserRoleKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Long insert(UserRoleDto userRoleDto) throws ServiceException {
        validateInsert(userRoleDto);
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userRoleDto, userRole);
        int i = userRoleMapper.insertSelective(userRole);
        return userRole.getId();
    }

    private void validateInsert(UserRoleDto userRoleDto) throws ServiceException {
        if (userRoleDto == null){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_IS_EMPTY);
        }

        Long roleId = userRoleDto.getRoleId();
        Long userId = userRoleDto.getUserId();
        if (roleId == null || roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }

        UserRoleDto dto = null;
        dto = selectByUserIdRoleId(userId, roleId);
        if (dto != null){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_HAS_ALREADY_EXISTED);
        }

    }

    @Override
    public List<RoleDto> selectOneByUsername(String username) throws ServiceException {
        if (StringUtils.isEmpty(username)){
            throw new ServiceException(HttpErrorEnum.USERNAME_PARAMETER_IS_EMPTY);
        }

        UserDto userDto = userService.selectOneByUsername(username);
        if (userDto == null){
            return null;
        }
        return selectAvailableByUserId(userDto.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(UserRoleDto userRoleDto) throws ServiceException {
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

    private void validateUpdate(UserRoleDto userRoleDto) throws ServiceException {
        if (userRoleDto == null){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_IS_EMPTY);
        }
        Long id = userRoleDto.getId();

        UserRoleDto dto = selectById(id);
        if (dto == null){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_ID_IS_NOT_EXIST);
        }
        Long roleId = userRoleDto.getRoleId();
        Long userId = userRoleDto.getUserId();
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_ID_PARAMETER_IS_EMPTY);
        }
        if (roleId != null && roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }
        if (userId != null && userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }
        if (userId != null){
            UserDto userDto = userService.selectById(userId);
            if (userDto == null){
                throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
            }
        }
        if (roleId != null){
            RoleDto roleDto = roleService.selectById(roleId);
            if (roleDto == null){
                throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
            }
        }
        if (roleId == null && userId != null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(userId, dto.getRoleId());
            if (userRoleDtoResult != null){
                throw new ServiceException(HttpErrorEnum.USER_ROLE_HAS_ALREADY_EXISTED);
            }
        }
        if (roleId != null && userId == null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(dto.getUserId(), roleId);
            if (userRoleDtoResult != null){
                throw new ServiceException(HttpErrorEnum.USER_ROLE_HAS_ALREADY_EXISTED);
            }
        }
        if (roleId != null && userId != null){
            UserRoleDto userRoleDtoResult = selectByUserIdRoleId(dto.getUserId(), dto.getRoleId());
            if (userRoleDtoResult != null){
                throw new ServiceException(HttpErrorEnum.USER_ROLE_HAS_ALREADY_EXISTED);
            }
        }
    }

    @Override
    public List<RoleDto> selectOneByMobile(String mobile) throws ServiceException {
        if (StringUtils.isEmpty(mobile)){
            throw new ServiceException(HttpErrorEnum.MOBILE_PARAMETER_IS_EMPTY);
        }
        UserDto userDto = userService.selectOneByMobile(mobile);
        if (userDto == null){
            return null;
        }

        Long userId = userDto.getId();
        return selectAvailableByUserId(userId);
    }

    @Override
    public List<RoleDto> selectAvailableByUserId(Long userId) throws ServiceException {
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andAvailableEqualTo(true);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)){
            return null;
        }

        List<Long> roleIds = new ArrayList<>();
        Map<Long, UserRole> userRoleMap = new HashMap<>();
        userRoles.forEach( userRole -> {
            Long roleId = userRole.getRoleId();
            roleIds.add(roleId);
            userRoleMap.put(roleId, userRole);
        });

        List<RoleDto> roleDtos = roleService.selectAvailableByIds(roleIds);

        //拼接用户角色的付费信息
        roleDtos.forEach( roleDto -> {
            UserRole userRole = userRoleMap.get(roleDto.getId());
            roleDto.setRemainder(userRole.getRemainder());
            roleDto.setEndTime(userRole.getEndTime());
        });
        return roleDtos;
    }

    @Override
    public UserRoleDto selectByUserIdRoleId(Long userId, Long roleId) throws ServiceException {
        if (roleId == null || roleId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_EMPTY);
        }
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
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
            throw new ServiceException(HttpErrorEnum.USER_ID_ROLE_ID_QUERY_MANY_RESULTS);
        }
    }

    @Override
    public UserRoleDto selectById(Long id) throws ServiceException {
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_ID_PARAMETER_IS_EMPTY);
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

    @Override
    public boolean updateRemainderByUserNameAndRoleId(String username, Long roleId) throws ServiceException {
        UserDto userDto = userService.selectOneByUsername(username);
        if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USERNAME_NOT_EXITED);
        }
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userDto.getId());
        criteria.andRoleIdEqualTo(roleId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
        if (CollectionUtils.isEmpty(userRoles)){
            throw new ServiceException(HttpErrorEnum.USER_ROLE_IS_EMPTY);
        }else if (userRoles.size() != 1){
            throw new ServiceException(HttpErrorEnum.USER_ID_ROLE_ID_QUERY_MANY_RESULTS);
        }else {
            UserRole userRole = userRoles.get(0);
            Long id = userRole.getId();
            int i = userRoleMapper.updateRemainderById(id);
            if (i == 1){
                return true;
            }else {
                return false;
            }

            /*UserRole userRole = userRoles.get(0);
            Long remainder = userRole.getRemainder();
            Long id = userRole.getId();
            UserRole userRoleTemp = new UserRole();
            userRoleTemp.setRemainder(remainder - 1L);
            userRoleTemp.setId(id);
            int i = userRoleMapper.updateByPrimaryKeySelective(userRoleTemp);
            if (i == 1){
                return true;
            }else {
                return false;
            }*/
        }
    }
}
