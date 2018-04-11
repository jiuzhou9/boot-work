package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.RandomStrUtil;
import com.jiuzhou.bootwork.common.CommonConstants;
import com.jiuzhou.bootwork.common.jwt.JwtTokenUtil;
import com.jiuzhou.bootwork.dao.mapper.AppMapper;
import com.jiuzhou.bootwork.dao.model.App;
import com.jiuzhou.bootwork.dao.model.AppExample;
import com.jiuzhou.bootwork.dao.model.AppKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.AppRoleService;
import com.jiuzhou.bootwork.service.AppService;
import com.jiuzhou.bootwork.service.RoleResourceService;
import com.jiuzhou.bootwork.service.RoleService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.AppDto;
import com.jiuzhou.bootwork.service.dto.AppRoleDto;
import com.jiuzhou.bootwork.service.dto.AppTokenDto;
import com.jiuzhou.bootwork.service.dto.RoleDto;
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

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/27
 */
@Service
@Slf4j
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private AppRoleService appRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 返回APP code
     * @param userToken
     * @param appName
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AppTokenDto insert(String userToken, String appName) throws ServiceException {
        UserDto userDto = userService.checkUserToken(userToken);
        Long userId = userDto.getId();
        App app = new App();
        app.setUserId(userId);
        app.setName(appName);
        validateInsert(app);

        //保证code不得重复
        while (StringUtils.isEmpty(app.getCode())){
            app.setCode(RandomStrUtil.getRandomString(CommonConstants.CODE_LENGTH));
            AppDto byCode = getByCode(app.getCode());
            if (byCode != null){
                app.setCode("");
            }
        }
        app.setSecret(UUID.randomUUID().toString());
        int i = appMapper.insertSelective(app);

        AppTokenDto appTokenDto = new AppTokenDto();
        appTokenDto.setCode(app.getCode());
        String appToken = JwtTokenUtil.generateAppToken(appName, app.getSecret());
        appTokenDto.setAppToken(appToken);
        return appTokenDto;
    }

    private void validateInsert(App app) throws ServiceException {
        if (app == null){
            throw new ServiceException(HttpErrorEnum.APP_IS_EMPTY);
        }
        String name = app.getName();
        Long userId = app.getUserId();
        if (StringUtils.isEmpty(name)){
            throw new ServiceException(HttpErrorEnum.APP_NAME_IS_EMPTY);
        }
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }

        AppDto appDtoResult = selectOneByNameUserId(name, userId);
        if (appDtoResult != null){
            throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
        }

    }

    private AppDto selectOneByNameUserId(String appName, Long userId) throws ServiceException {
        if (StringUtils.isEmpty(appName)){
            throw new ServiceException(HttpErrorEnum.APP_NAME_IS_EMPTY);
        }
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }

        UserDto userDto = userService.selectById(userId);
        if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
        }

        AppExample appExample = new AppExample();
        AppExample.Criteria criteria = appExample.createCriteria();
        criteria.andNameEqualTo(appName);
        criteria.andUserIdEqualTo(userId);
        List<App> apps = appMapper.selectByExample(appExample);
        if (CollectionUtils.isEmpty(apps)){
            return null;
        }else if (apps.size() == 1){
            AppDto appDto = new AppDto();
            BeanUtils.copyProperties(apps.get(0), appDto);
            return appDto;
        }else {
            throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_QUERY_MANY_RESULTS);
        }
    }

    private AppDto selectOneAvailableByNameUserId(String appName, Long userId) throws ServiceException {
        AppDto appDto = selectOneByNameUserId(appName, userId);
        if (appDto.getAvailable()){
            return appDto;
        }else {
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    protected boolean updateById(AppDto appDto) throws ServiceException {
        validateUpdate(appDto);
        App app = new App();
        BeanUtils.copyProperties(appDto, app);
        int i = appMapper.updateByPrimaryKeySelective(app);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    private void validateUpdate(AppDto appDto) throws ServiceException {
        if (appDto == null){
            throw new ServiceException(HttpErrorEnum.APP_IS_EMPTY);
        }
        Long id = appDto.getId();
        String name = appDto.getName();
        Long userId = appDto.getUserId();
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.APP_ID_IS_EMPTY);
        }
        if (userId != null && userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }


        AppDto appDtoResult = selectById(id);
        if (appDtoResult == null){
            throw new ServiceException(HttpErrorEnum.APP_ID_IS_NOT_EXIST);
        }
        if (!StringUtils.isEmpty(name) && userId == null){
            AppDto dto = selectOneAvailableByNameUserId(name, appDtoResult.getUserId());
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
            }
        }
        if (StringUtils.isEmpty(name) && userId != null){
            AppDto dto = selectOneAvailableByNameUserId(appDtoResult.getName(), userId);
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
            }
        }
        if (!StringUtils.isEmpty(name) && userId != null){
            AppDto dto = selectOneAvailableByNameUserId(name, userId);
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
            }
        }
    }

    @Override
    public AppDto selectById(Long id) throws ServiceException {
        if (id == null || id.equals(0L)){
            throw new ServiceException(HttpErrorEnum.APP_ID_IS_EMPTY);
        }
        AppKey appKey = new AppKey();
        appKey.setId(id);
        App app = appMapper.selectByPrimaryKey(appKey);
        if (app == null){
            return null;
        }

        AppDto appDto = new AppDto();
        BeanUtils.copyProperties(app, appDto);
        return appDto;
    }

    private List<AppDto> selectByUserId(Long userId) throws ServiceException {
        if (userId == null || userId.equals(0L)){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_EMPTY);
        }
        UserDto userDto = userService.selectById(userId);
        if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
        }
        Long userDtoId = userDto.getId();
        AppExample appExample = new AppExample();
        AppExample.Criteria criteria = appExample.createCriteria();
        criteria.andUserIdEqualTo(userDtoId);
        List<App> apps = appMapper.selectByExample(appExample);
        if (CollectionUtils.isEmpty(apps)){
            return null;
        }

        List<AppDto> appDtos = new ArrayList();
        apps.forEach(app -> {
            AppDto appDto = new AppDto();
            BeanUtils.copyProperties(app, appDto);
            appDtos.add(appDto);
        });
        return appDtos;
    }

    @Override
    public AppTokenDto getAppToken(String userToken, String appName) throws ServiceException {
        UserDto userDto = userService.checkUserToken(userToken);
        Long userId = userDto.getId();
        AppDto appDto = selectOneByNameUserId(appName, userId);
        if (appDto == null){
            throw new ServiceException(HttpErrorEnum.APP_NAME_IS_NOT_EXIST);
        }else if (!appDto.getAvailable()){
            throw new ServiceException(HttpErrorEnum.APP_IS_NOT_AVAILABLE);
        }
        AppDto dto = new AppDto();
        dto.setId(appDto.getId());
        dto.setSecret(UUID.randomUUID().toString());
        boolean b = updateById(dto);
        if (b){
            String appToken = JwtTokenUtil.generateAppToken(appName, dto.getSecret());
            AppTokenDto appTokenDto = new AppTokenDto();
            appTokenDto.setAppToken(appToken);
            appTokenDto.setCode(appDto.getCode());
            return appTokenDto;
        }
        throw new ServiceException(HttpErrorEnum.APP_UPDATE_FAILED);
    }

    @Override
    public AppTokenDto checkAppToken(String appToken, String code) throws ServiceException {
        if (StringUtils.isEmpty(appToken)) {
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_IS_EMPTY);
        }else if (StringUtils.isEmpty(code)){
            throw new ServiceException(HttpErrorEnum.APP_CODE_IS_EMPTY);
        }
        AppDto byCode = getByCode(code);
        if(byCode == null){
            throw new ServiceException(HttpErrorEnum.APP_CODE_IS_NOT_EXIT);
        }else if (!byCode.getAvailable()){
            throw new ServiceException(HttpErrorEnum.APP_IS_NOT_AVAILABLE);
        }
        String appName = JwtTokenUtil.getAppName(appToken, byCode.getSecret());
        if (StringUtils.isEmpty(appName) || !appName.equals(byCode.getName())){
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_IS_NOT_RIGHT);
        }
        Boolean aBoolean = JwtTokenUtil.checkAppTokenExpired(appToken, byCode.getSecret());
        if (aBoolean){
            throw new ServiceException(HttpErrorEnum.APP_TOKEN_IS_EXPIRED);
        }
        Long userId = byCode.getUserId();
        UserDto userDto = userService.selectById(userId);
        if (userDto != null && userDto.getAvailable()){
            AppTokenDto appTokenDto = new AppTokenDto();
            appTokenDto.setAppName(appName);
            appTokenDto.setUserName(userDto.getUsername());
            return appTokenDto;
        }else if (userDto == null){
            throw new ServiceException(HttpErrorEnum.USER_ID_IS_NOT_EXIST);
        }else {
            throw new ServiceException(HttpErrorEnum.USER_IS_NOT_AVAILABLE);
        }

    }

    private AppDto getByCode(String code) throws ServiceException {
        if (StringUtils.isEmpty(code)){
            throw new ServiceException(HttpErrorEnum.APP_CODE_IS_EMPTY);
        }
        AppExample appExample = new AppExample();
        AppExample.Criteria criteria = appExample.createCriteria();
        criteria.andCodeEqualTo(code);
        List<App> apps = appMapper.selectByExample(appExample);
        if (CollectionUtils.isEmpty(apps)){
            return null;
        }else if (apps.size() != 1){
            throw new ServiceException(HttpErrorEnum.APP_CODE_IS_EMPTY);
        }else {
            AppDto appDto = new AppDto();
            BeanUtils.copyProperties(apps.get(0), appDto);
            return appDto;
        }
    }

//    @Override
//    public boolean decide(AppTokenDto appTokenDto) throws ServiceException {
//        validateDecide(appTokenDto);
//        String userName = appTokenDto.getUserName();
//        String appName = appTokenDto.getAppName();
//        String serverResource = appTokenDto.getServerResource();
//        String methodType = appTokenDto.getMethodType();
//        boolean decide = roleResourceService.decide(userName, serverResource, methodType);
//
//        return false;
//    }

    @Override
    public AppDto getAvailableByAppNameUserName(String appName, String userName) throws ServiceException {
        UserDto userDto = userService.selectOneAvailableWithRolesByUsername(userName);
        if (userDto == null){
            return null;
        }

        Long userId = userDto.getId();
        AppDto appDto = selectOneAvailableByNameUserId(appName, userId);
        if (appDto == null){
            return null;
        }

        Long id = appDto.getId();
        List<AppRoleDto> appRoleDtos = appRoleService.selectAvailableByAppId(id);
        if (CollectionUtils.isEmpty(appRoleDtos)){
            return appDto;
        }

        List<Long> roleIds = new ArrayList<>();
        appRoleDtos.forEach( appRoleDto -> {
            roleIds.add(appRoleDto.getRoleId());
        });

        List<RoleDto> roleDtos =  roleService.selectAvailableByIds(roleIds);
        List<String> roleNames = new ArrayList<>();
        roleDtos.forEach( roleDto -> {
            roleNames.add(roleDto.getName());
        });

        appDto.setRoleNames(roleNames);

        return appDto;
    }
}
