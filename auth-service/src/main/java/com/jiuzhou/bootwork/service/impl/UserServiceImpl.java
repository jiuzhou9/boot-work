package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.UserMapper;
import com.jiuzhou.bootwork.dao.model.User;
import com.jiuzhou.bootwork.dao.model.UserExample;
import com.jiuzhou.bootwork.dao.model.UserKey;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(UserDto userDto) throws Exception {
        validateInsert(userDto);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int i = userMapper.insertSelective(user);
        return user.getId();
    }

    private void validateInsert(UserDto userDto) throws Exception {
        if (userDto == null){
            throw new Exception("用户信息为空");
        }
        String mobile = userDto.getMobile();
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StringUtils.isEmpty(mobile)){
            throw new Exception("手机号为空");
        }
        if (StringUtils.isEmpty(username)){
            throw new Exception("用户名为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new Exception("密码为空");
        }

        UserDto dto = null;
        dto = selectOneByUsername(username);
        if (dto != null){
            throw new Exception("用户名已经存在");
        }

        dto = selectOneByMobile(mobile);
        if (dto != null){
            throw new Exception("手机号码已经存在");
        }
    }

    @Override
    public UserDto selectOneByUsername(String username) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() == 1){
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(users.get(0), dto);
            return dto;
        }else {
            throw new Exception("该用户名查询到多条数据");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(UserDto userDto) throws Exception {
        validateUpdate(userDto);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(UserDto userDto) throws Exception {
        if (userDto == null){
            throw new Exception("用户信息为空");
        }
        Long id = userDto.getId();
        if (id == null || id.equals(0L)){
            throw new Exception("ID为空");
        }
        UserDto dto = null;
        dto = selectById(id);
        if (dto == null){
            throw new Exception("ID信息不存在");
        }

        dto = null;
        String mobile = userDto.getMobile();
        if (!StringUtils.isEmpty(mobile)){
            dto = selectOneByMobile(mobile);
            if (dto != null){
                throw new Exception("手机号已存在");
            }
        }

        String username = userDto.getUsername();
        if (!StringUtils.isEmpty(username)){
            dto = selectOneByUsername(username);
            if (dto != null){
                throw new Exception("用户名已存在");
            }
        }
    }

    @Override
    public UserDto selectOneByMobile(String mobile) throws Exception {
        if (StringUtils.isEmpty(mobile)){
            throw new Exception("mobile为空");
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() == 1){
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(users.get(0), dto);
            return dto;
        }else {
            throw new Exception("查询到多条数据");
        }
    }

    @Override
    public UserDto selectById(Long id) throws Exception {
        if (id == null || id.equals(0L)){
            throw new Exception("ID为空");
        }
        UserKey userKey = new UserKey();
        userKey.setId(id);
        User user = userMapper.selectByPrimaryKey(userKey);
        if (user == null){
            throw new Exception("ID不存在");
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
