package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.UserMapper;
import com.jiuzhou.bootwork.dao.model.User;
import com.jiuzhou.bootwork.dao.model.UserExample;
import com.jiuzhou.bootwork.dao.model.UserKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.jwt.JwtTokenUtil;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import com.jiuzhou.bootwork.service.dto.UserTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public Long insert(UserDto userDto) throws ServiceException {
        validateInsert(userDto);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int i = userMapper.insertSelective(user);
        return user.getId();
    }

    private void validateInsert(UserDto userDto) throws ServiceException {
        if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USER_IS_EMPTY);
        }
        String mobile = userDto.getMobile();
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        String nickName = userDto.getNickName();
        if (StringUtils.isEmpty(nickName)){
            userDto.setNickName(username);
        }

        if (StringUtils.isEmpty(mobile)){
            throw new ServiceException(HttpErrorEnum.MOBILE_PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isEmpty(username)){
            throw new ServiceException(HttpErrorEnum.USERNAME_PARAMETER_IS_EMPTY);
        }
        if (StringUtils.isEmpty(password)){
            throw new ServiceException(HttpErrorEnum.PASSWORD_PARAMETER_IS_EMPTY);
        }

        UserDto dto = null;
        dto = selectOneByUsername(username);
        if (dto != null){
            throw new ServiceException(HttpErrorEnum.USERNAME_HAS_ALREADY_EXISTED);
        }

        dto = selectOneByMobile(mobile);
        if (dto != null){
            throw new ServiceException(HttpErrorEnum.MOBILE_HAS_ALREADY_EXISTED);
        }
    }

    @Override
    public UserDto selectOneByUsername(String username) throws ServiceException {
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
            throw new ServiceException(HttpErrorEnum.USERNAME_PARAMETER_QUERY_MANY_RESULTS);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(UserDto userDto) throws ServiceException {
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

    private void validateUpdate(UserDto userDto) throws ServiceException {
        if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USER_IS_EMPTY);
        }
        Long id = userDto.getId();
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }
        UserDto dto = null;
        dto = selectById(id);
        if (dto == null){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
        }

        dto = null;
        String mobile = userDto.getMobile();
        if (!StringUtils.isEmpty(mobile)){
            dto = selectOneByMobile(mobile);
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.MOBILE_HAS_ALREADY_EXISTED);
            }
        }

        String username = userDto.getUsername();
        if (!StringUtils.isEmpty(username)){
            dto = selectOneByUsername(username);
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USERNAME_HAS_ALREADY_EXISTED);
            }
        }
    }

    @Override
    public UserDto selectOneByMobile(String mobile) throws ServiceException {
        if (StringUtils.isEmpty(mobile)){
            throw new ServiceException(HttpErrorEnum.MOBILE_PARAMETER_IS_EMPTY);
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
            throw new ServiceException(HttpErrorEnum.MOBILE_PARAMETER_QUERY_MANY_RESULTS);
        }
    }

    @Override
    public UserDto selectById(Long id) throws ServiceException {
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }
        UserKey userKey = new UserKey();
        userKey.setId(id);
        User user = userMapper.selectByPrimaryKey(userKey);
        if (user == null){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
        }
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public UserTokenDto register(UserDto userDto) throws ServiceException {
        validateInsert(userDto);
        String password = userDto.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        password = bCryptPasswordEncoder.encode(password);
        userDto.setPassword(password);
        Long insert = insert(userDto);

        //生成jwttoken
        UserTokenDto userToken = createUserToken(userDto.getUsername());
        return userToken;
    }

    /**
     * 可以根据用户名直接生成用户的令牌，一般此方法被登录调用
     * @param username
     * @return
     */
    protected UserTokenDto createUserToken(String username) {
        String userToken = JwtTokenUtil.generateUserToken(username);
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setUserToken(userToken);
        return userTokenDto;
    }

    @Override
    public UserTokenDto login(String username, String password) throws ServiceException {

        return null;
    }
}
