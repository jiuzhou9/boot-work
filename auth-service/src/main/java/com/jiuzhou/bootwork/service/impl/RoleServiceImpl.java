package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.RoleMapper;
import com.jiuzhou.bootwork.dao.mapper.UserRoleMapper;
import com.jiuzhou.bootwork.dao.model.Role;
import com.jiuzhou.bootwork.dao.model.RoleExample;
import com.jiuzhou.bootwork.dao.model.RoleKey;
import com.jiuzhou.bootwork.dao.model.UserRoleExample;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.UserRoleService;
import com.jiuzhou.bootwork.service.dto.RoleDto;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(RoleDto roleDto) throws ServiceException {
        validateInsert(roleDto);
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int i = roleMapper.insertSelective(role);
        return role.getId();
    }

    private void validateInsert(RoleDto roleDto) throws ServiceException {
        if (roleDto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_IS_EMPTY);
        }
        String name = roleDto.getName();
        if (StringUtils.isEmpty(name)){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_IS_EMPTY);
        }

        RoleDto roleDtoTemp = selectOneByName(name);
        if (roleDtoTemp != null){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_HAS_ALREADY_EXISTED);
        }
    }

    @Override
    public List<RoleDto> selectLikeName(String name) throws ServiceException {
        if (StringUtils.isEmpty(name)){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_IS_EMPTY);
        }
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameLike(name);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach( role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        });
        return roleDtos;
    }

    @Override
    public RoleDto selectOneByName(String name) throws ServiceException {
        if (StringUtils.isEmpty(name)){
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_IS_EMPTY);
        }
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (CollectionUtils.isEmpty(roles)){
            return null;
        }else if (roles.size() == 1){
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(roles.get(0), roleDto);
            return roleDto;
        }else {
            throw new ServiceException(HttpErrorEnum.ROLE_NAME_PARAMETER_QUERY_MANY_RESULTS);
        }
    }

    @Override
    public RoleDto selectById(Long key) throws ServiceException {
        if (key == null || key.equals(0L)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }
        RoleKey roleKey = new RoleKey();
        roleKey.setId(key);
        Role role = roleMapper.selectByPrimaryKey(roleKey);
        if (role == null){
            return null;
        }
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateByKey(RoleDto roleDto) throws ServiceException {
        validateUpdate(roleDto);
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int i = roleMapper.updateByExampleSelective(role);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(RoleDto roleDto) throws ServiceException {
        if (roleDto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_IS_EMPTY);
        }
        Long id = roleDto.getId();
        RoleDto dto = selectById(id);
        if (dto == null){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }else if (!dto.getName().equals(roleDto.getName())){
            RoleDto roleDtoTemp = selectOneByName(roleDto.getName());
            if (roleDtoTemp != null){
                throw new ServiceException(HttpErrorEnum.ROLE_NAME_HAS_ALREADY_EXISTED);
            }
        }
    }

    @Override
    public List<RoleDto> selectAvailableByIds(List<Long> ids) throws ServiceException {
        if (CollectionUtils.isEmpty(ids)){
            throw new ServiceException(HttpErrorEnum.ROLE_ID_IS_NOT_EXIST);
        }
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);
        criteria.andAvailableEqualTo(true);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach( role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        });
        return roleDtos;
    }

    @Override
    public List<RoleDto> selectAvilable() {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andAvailableEqualTo(true);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (CollectionUtils.isEmpty(roles)){
            return null;
        }
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach( role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        });
        return roleDtos;
    }

}
