package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.RoleMapper;
import com.jiuzhou.bootwork.dao.model.Role;
import com.jiuzhou.bootwork.dao.model.RoleExample;
import com.jiuzhou.bootwork.dao.model.RoleKey;
import com.jiuzhou.bootwork.service.RoleService;
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
    public Long insert(RoleDto roleDto) throws Exception {
        validateInsert(roleDto);
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int i = roleMapper.insertSelective(role);
        return role.getId();
    }

    private void validateInsert(RoleDto roleDto) throws Exception {
        if (roleDto == null){
            throw new Exception("角色信息为空");
        }
        String name = roleDto.getName();
        if (StringUtils.isEmpty(name)){
            throw new Exception("角色名称为空");
        }

        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (!CollectionUtils.isEmpty(roles)){
            throw new Exception("角色名称已存在");
        }
    }

    @Override
    public List<RoleDto> selectByName(String name) throws Exception {
        if (StringUtils.isEmpty(name)){
            throw new Exception("name为空");
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
    public RoleDto selectOneByName(String name) throws Exception {
        if (StringUtils.isEmpty(name)){
            throw new Exception("name为空");
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
            throw new Exception("该角色名字对应多条数据");
        }
    }

    @Override
    public RoleDto selectById(Long key) throws Exception {
        if (key == null || key.equals(0L)){
            throw new Exception("ID为空");
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
    public boolean updateByKey(RoleDto roleDto) throws Exception {
        validateUpdate(roleDto);
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        int i = roleMapper.updateByPrimaryKey(role);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(RoleDto roleDto) throws Exception {
        if (roleDto == null){
            throw new Exception("角色信息为空");
        }
        Long id = roleDto.getId();
        RoleDto dto = selectById(id);
        if (dto == null){
            throw new Exception("角色信息不存在");
        }
    }

    @Override
    public List<RoleDto> selectByIds(List<Long> ids) throws Exception {
        if (CollectionUtils.isEmpty(ids)){
            throw new Exception("ids为空");
        }
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach( role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        });
        return roleDtos;
    }
}
