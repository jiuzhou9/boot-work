package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.common.UUIDUtil;
import com.jiuzhou.bootwork.common.UserConstants;
import com.jiuzhou.bootwork.dao.mapper.UserMapper;
import com.jiuzhou.bootwork.dao.model.User;
import com.jiuzhou.bootwork.dao.model.UserExample;
import com.jiuzhou.bootwork.dao.model.UserKey;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.service.CompanyService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.CompanyDTO;
import com.jiuzhou.bootwork.service.dto.UserDTO;
import com.jiuzhou.bootwork.service.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/15
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyService companyService;

    /**
     * 邮箱注册用户
     * @param userDTO 用户
     * @return 用户
     */
    private UserDTO registerByEmail(UserDTO userDTO) throws ApiGateWayException {

        validateRegister(userDTO, UserConstants.REGISTER_TYPE_EMAIL);

        userDTO.setUserCode(null);
        userDTO.setMobile(null);

        dealUserCode(userDTO);

        userDTO = add(userDTO);

        return userDTO;
    }

    /**
     * 设置userCode，保证唯一性
     * @param userDTO 用户
     * @throws ApiGateWayException 当code查询到多条数据的时候抛异常
     */
    private void dealUserCode(UserDTO userDTO) throws ApiGateWayException {
        while (StringUtils.isEmpty(userDTO.getUserCode())){
            String userCode = UUIDUtil.generator();
            UserDTO userTemp = getByUserCode(userCode);
            if (userTemp == null){
                userDTO.setUserCode(userCode);
            }
        }
    }

    /**
     * 校验用户注册信息
     * 1.根据注册类型type的不同进行校验：
     *  1.1邮箱注册
     *  1.2手机注册
     *  1.3手机+邮箱注册
     *  1.4其他情况（暂时抛异常处理）
     * 2.用户名、密码校验
     * 3.如果公司编码非空，那么校验公司编码，是否存在
     *
     * @param userDTO 用户，非空
     * @param type 用户注册类型：邮箱或者手机
     *             @see UserConstants#REGISTER_TYPE_EMAIL
     *             @see UserConstants#REGISTER_TYPE_MOBILE
     *
     * @throws ApiGateWayException
     */
    private void validateRegister(UserDTO userDTO, String type) throws ApiGateWayException {

        if (UserConstants.REGISTER_TYPE_EMAIL.equals(type)){
            validateRegisterByEmail(userDTO);

        }else if (UserConstants.REGISTER_TYPE_MOBILE.equals(type)){
            validateregisterByMobile(userDTO);

        }else if (UserConstants.REGISTER_TYPE_MOBILE_EMAIL.equals(type)){
            validateRegisterByMobileEmail(userDTO);

        }else {
            log.error("用户注册方式错误，参数(type)：" + type);
            throw new ApiGateWayException(HttpErrorEnum.USER_REGISTER_TYPE_IS_ERROR);
        }

        validateUserNamePassword(userDTO);

        if (!StringUtils.isEmpty(userDTO.getCompanyCode()) && !StringUtils.isEmpty(userDTO.getCompanyCode().trim())){
            CompanyDTO companyDTO = companyService.getByCodeWithRedisAndDB(userDTO.getCompanyCode());
            if (companyDTO == null) {
                throw new ApiGateWayException(HttpErrorEnum.COMPANY_CODE_IS_NOT_EXISTED);
            }
        }

    }

    /**
     * 校验用户的用户名和密码
     * @param userDTO
     * @throws ApiGateWayException
     */
    private void validateUserNamePassword(UserDTO userDTO) throws ApiGateWayException {

        String username = userDTO.getUsername().trim();
        String password = userDTO.getPassword();
        if (StringUtils.isEmpty(username)){
            log.error("用户名为空，用户参数为：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_USERNAME_IS_EMPTY);
        }

        if (StringUtils.isEmpty(password)){
            log.error("用户密码为空，用户参数为：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_PASSWORD_IS_EMPTY);
        }

        UserDTO userTemp = getByUserName(username);
        if (userTemp != null){
            log.error("用户名已经存在，用户参数：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USERNAME_HAS_ALREADY_EXISTED);
        }


    }

    /**
     * 校验手机邮箱并存的注册方式
     * @param userDTO
     * @throws ApiGateWayException
     */
    private void validateRegisterByMobileEmail(UserDTO userDTO) throws ApiGateWayException {
        validateregisterByMobile(userDTO);

        validateRegisterByEmail(userDTO);
    }

    /**
     * 校验手机注册方式（后续可以根据需求加校验规则）
     * @param userDTO
     * @throws ApiGateWayException
     */
    private void validateregisterByMobile(UserDTO userDTO) throws ApiGateWayException {

        String mobile = userDTO.getMobile().trim();
        if (StringUtils.isEmpty(mobile)){
            log.error("用户注册类型为手机注册，用户手机为空，参数为：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_MOBILE_IS_EMPTY);
        }

        UserDTO userTemp = getByMobile(mobile);
        if (userTemp != null){
            log.error("用户手机号码已经存在，参数：" + mobile);
            throw new ApiGateWayException(HttpErrorEnum.USER_MOBILE_HAS_ALREADY_EXISTED);
        }
    }

    /**
     * 邮箱方式注册，规则校验(后续可以根据需求加规则校验)
     * @param userDTO
     * @throws ApiGateWayException
     */
    private void validateRegisterByEmail(UserDTO userDTO) throws ApiGateWayException {
        String email = userDTO.getEmail().trim();
        if (StringUtils.isEmpty(email)){
            log.error("用户注册类型为邮箱注册，用户邮箱为空，参数为：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_EMAIL_IS_EMPTY);
        }

        UserDTO userTemp = getByEmail(email);
        if (userTemp != null){
            log.error("用户邮箱已经存在，参数：" + email);
            throw new ApiGateWayException(HttpErrorEnum.USER_EMAIL_HAS_ALREADY_EXISTED);
        }
    }

    /**
     * 用户条件查询
     * @param email 查询条件邮箱
     * @return 返回唯一的一个用户或者空
     * @throws ApiGateWayException 如果查询到多条用户信息，那么抛异常
     */
    private UserDTO getByEmail(String email) throws ApiGateWayException {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() > 1){
            log.error("根据用户邮箱查询得到多条数据，查询参数为：" + email);
            throw new ApiGateWayException(HttpErrorEnum.USER_EMAIL_QUERY_MANY_RESULTS);
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(users.get(0), userDTO);
            return userDTO;
        }
    }

    /**
     * 用户条件查询
     * @param mobile 查询条件手机
     * @return 返回唯一的一个用户或者空
     * @throws ApiGateWayException 如果查询到多条用户信息，那么抛异常
     */
    private UserDTO getByMobile(String mobile) throws ApiGateWayException {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andMobileEqualTo(mobile);
        List<User> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() > 1){
            log.error("根据用户手机查询得到多条数据，查询参数为：" + mobile);
            throw new ApiGateWayException(HttpErrorEnum.USER_MOBILE_QUERY_MANY_RESULTS);
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(users.get(0), userDTO);
            return userDTO;
        }
    }

    /**
     * 手机注册用户
     * @param userDTO 用户
     * @return 用户
     */
    private UserDTO registerByMobile(UserDTO userDTO) throws ApiGateWayException {

        validateRegister(userDTO, UserConstants.REGISTER_TYPE_MOBILE);

        userDTO.setUserCode(null);
        userDTO.setEmail(null);

        dealUserCode(userDTO);

        userDTO = add(userDTO);

        return userDTO;
    }

    /**
     * 插入用户
     * 用户密码加密
     *
     * @param userDTO 用户
     * @return 用户信息，包含用户ID
     * @throws ApiGateWayException
     */
    private UserDTO add(UserDTO userDTO) throws ApiGateWayException {

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        String password = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        password = bCryptPasswordEncoder.encode(password);
        user.setPassword(password);

        int insert = userMapper.insertSelective(user);
        if (insert == 1){
            userDTO.setId(user.getId());
        }else {
            log.error("用户插入失败，参数：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_MOBILE_QUERY_MANY_RESULTS);
        }

        return userDTO;
    }

    @Override
    public UserDTO getByUserCode(String userCode) throws ApiGateWayException {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() > 1){
            log.error("根据用户code查询得到多条数据，查询参数为：" + userCode);
            throw new ApiGateWayException(HttpErrorEnum.USER_CODE_QUERY_MANY_RESULTS);
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(users.get(0), userDTO);
            return userDTO;
        }
    }

    /**
     * 用户条件查询
     * @param username 查询条件用户名
     * @return 唯一的用户
     */
    private UserDTO getByUserName(String username) throws ApiGateWayException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(username.trim())){
            return null;
        }

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);

        if (CollectionUtils.isEmpty(users)){
            return null;
        }else if (users.size() > 1){
            log.error("用户名查询到多条用户数据，用户名为：" + username);
            throw new ApiGateWayException(HttpErrorEnum.USERNAME_QUERY_MANY_RESULTS);
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(users.get(0), userDTO);
            return userDTO;
        }
    }

    @Override
    public Integer register(UserDTO userDTO) throws ApiGateWayException {

        if (userDTO == null) {
            throw new ApiGateWayException(HttpErrorEnum.USER_IS_EMPTY);
        }

        dealName(userDTO);

        String mobile = userDTO.getMobile().trim();
        String email = userDTO.getEmail().trim();

        if (StringUtils.isEmpty(email)) {
            userDTO = registerByMobile(userDTO);

        } else if (StringUtils.isEmpty(mobile)) {
            userDTO = registerByEmail(userDTO);

        }else {
            validateRegister(userDTO, UserConstants.REGISTER_TYPE_MOBILE_EMAIL);

            userDTO.setUserCode(null);
            dealUserCode(userDTO);

            //入库
            userDTO = add(userDTO);
        }
        return userDTO.getId();
    }

    /**
     * 处理用户名、用户昵称
     * @param userDTO
     * @throws ApiGateWayException
     */
    private void dealName(UserDTO userDTO) throws ApiGateWayException {
        String username = userDTO.getUsername().trim();
        String mobile = userDTO.getMobile().trim();
        String email = userDTO.getEmail().trim();
        String nickname = userDTO.getNickname().trim();

        if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) {
            log.error("用户注册手机号、邮箱均为空：" + JSON.toJSONString(userDTO));
            throw new ApiGateWayException(HttpErrorEnum.USER_MOBILE_EMAIL_IS_EMPTY);
        }

        if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(mobile)) {
            userDTO.setUsername(mobile);
            username = mobile;
        } else if (StringUtils.isEmpty(username) && !StringUtils.isEmpty(email)) {
            userDTO.setUsername(email);
            username = email;
        }

        if (StringUtils.isEmpty(nickname)){
            userDTO.setNickname(username);
        }
    }

    @Override
    public Integer login(UserLoginDTO userLoginDTO) throws ApiGateWayException {
        if (userLoginDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.USER_LOGIN_IS_EMPTY);
        }

        String userStr = userLoginDTO.getUserStr().trim();
        String password = userLoginDTO.getPassword();

        if (StringUtils.isEmpty(userStr) || StringUtils.isEmpty(password)){
            throw new ApiGateWayException(HttpErrorEnum.USER_LOGIN_IS_EMPTY);
        }

        UserDTO temp;
        boolean flag;

        temp = getByUserName(userStr);
        flag = matchPassword(temp, password);

        if (!flag){
            temp = getByMobile(userStr);
            flag = matchPassword(temp, password);

            if (!flag){
                temp = getByEmail(userStr);
                flag = matchPassword(temp, password);
            }
        }

        if (flag){
            return temp.getId();
        }else {
            throw new ApiGateWayException(HttpErrorEnum.USER_LOGIN_IS_ERROR);
        }
    }

    /**
     * 如果用户的明文密码与数据库中的密码密文匹配成功，那么返回true，否则返回false
     * @param userDTO 数据库查询出来的用户对象
     * @param password 用户登录的明文密码
     * @return true or false
     */
    private boolean matchPassword(UserDTO userDTO, String password){

        if (userDTO != null){

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean flag = bCryptPasswordEncoder.matches(password, userDTO.getPassword());
            return flag;
        }

        return false;
    }

    @Override
    public UserDTO getById(Integer userId) {
        if (userId == null || userId == 0){
            return null;
        }

        UserKey userKey = new UserKey();
        userKey.setId(userId);
        User user = userMapper.selectByPrimaryKey(userKey);

        if (user == null){
            return null;
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }
    }

    @Override
    public UserDTO getAvailableUserById(Integer userId) {
        UserDTO userDTO = getById(userId);
        if (userDTO.getActive()){
            return userDTO;
        }else {
            return null;
        }
    }

    @Override
    public boolean updateUserCompanyCode(Integer userId, String companyCode) throws ApiGateWayException {
        if (StringUtils.isEmpty(companyCode)){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_CODE_IS_EMPTY);
        }

        User user = new User();
        user.setId(userId);
        user.setCompanyCode(companyCode);
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }
}
