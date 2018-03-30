package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.dao.mapper.AppMapper;
import com.jiuzhou.bootwork.dao.model.App;
import com.jiuzhou.bootwork.dao.model.AppExample;
import com.jiuzhou.bootwork.dao.model.AppKey;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.excep.ServiceException;
import com.jiuzhou.bootwork.service.AppService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.AppDto;
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

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Long insert(AppDto appDto) throws ServiceException {
        validateInsert(appDto);
        App app = new App();
        BeanUtils.copyProperties(appDto, app);
        int i = appMapper.insertSelective(app);
        return app.getId();
    }

    private void validateInsert(AppDto appDto) throws ServiceException {
        if (appDto == null){
            throw new ServiceException(HttpErrorEnum.APP_IS_EMPTY);
        }
        String name = appDto.getName();
        Long userId = appDto.getUserId();
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

    @Override
    public AppDto selectOneByNameUserId(String name, Long userId) throws ServiceException {
        if (StringUtils.isEmpty(name)){
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
        criteria.andNameEqualTo(name);
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public boolean updateById(AppDto appDto) throws ServiceException {
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
            AppDto dto = selectOneByNameUserId(name, appDtoResult.getUserId());
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
            }
        }
        if (StringUtils.isEmpty(name) && userId != null){
            AppDto dto = selectOneByNameUserId(appDtoResult.getName(), userId);
            if (dto != null){
                throw new ServiceException(HttpErrorEnum.USER_ID_APP_NAME_HAS_ALREADY_EXISTED);
            }
        }
        if (!StringUtils.isEmpty(name) && userId != null){
            AppDto dto = selectOneByNameUserId(name, userId);
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

    @Override
    public List<AppDto> selectByUserId(Long userId) throws ServiceException {
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
}
