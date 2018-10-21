package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.bootwork.RandomStrUtil;
import com.jiuzhou.bootwork.cache.AccesskeyCacheManage;
import com.jiuzhou.bootwork.common.CallBackConstants;
import com.jiuzhou.bootwork.common.MD5Util;
import com.jiuzhou.bootwork.common.TimeStampConstants;
import com.jiuzhou.bootwork.common.UUIDUtil;
import com.jiuzhou.bootwork.common.jwt.JwtUtil;
import com.jiuzhou.bootwork.dao.mapper.AccesskeyMapper;
import com.jiuzhou.bootwork.dao.model.Accesskey;
import com.jiuzhou.bootwork.dao.model.AccesskeyExample;
import com.jiuzhou.bootwork.dao.model.AccesskeyKey;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.service.AccesskeyService;
import com.jiuzhou.bootwork.service.CompanyService;
import com.jiuzhou.bootwork.service.QuotaService;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/17
 */
@Service
@Slf4j
public class AccesskeyServiceImpl implements AccesskeyService {

    @Autowired
    private UserService userService;

    @Autowired
    private AccesskeyMapper accesskeyMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccesskeyCacheManage accesskeyCacheManage;

    @Override
    public AccesskeyTokenDTO create(String keyName, Integer userId) throws ApiGateWayException {
        AccesskeyDTO accesskeyDTO = new AccesskeyDTO();
        accesskeyDTO.setName(keyName);

        UserDTO userDTO = userService.getById(userId);
        accesskeyDTO.setUserCode(userDTO != null ? userDTO.getUserCode() : null);

        validateCreate(accesskeyDTO);

        String secret = UUIDUtil.generator();
        accesskeyDTO.setSecret(secret);

        String key = UUIDUtil.generator();
        accesskeyDTO.setKey(key);

        String privateSecret = UUIDUtil.generator() + RandomStrUtil.getRandomString(3);
        accesskeyDTO.setPrivateSecret(privateSecret);

        Accesskey accesskey = new Accesskey();
        BeanUtils.copyProperties(accesskeyDTO, accesskey);
        insertSelectiveRedisAndDB(accesskeyDTO);

        LocalDateTime expireTime;
        if (accesskeyDTO.getExpireTime() != null){
            expireTime = accesskeyDTO.getExpireTime();
        }else {
            accesskey = getById(accesskey.getId());
            expireTime = accesskey.getExpireTime();
        }

        String keyToken = JwtUtil.generateAppToken(accesskey.getKey(), accesskey.getPrivateSecret(), Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()));

        AccesskeyTokenDTO accesskeyTokenDTO = new AccesskeyTokenDTO();
        accesskeyTokenDTO.setKey(accesskey.getKey());
        accesskeyTokenDTO.setName(accesskey.getName());
        accesskeyTokenDTO.setSecret(accesskey.getSecret());
        accesskeyTokenDTO.setKeyToken(keyToken);


        return accesskeyTokenDTO;
    }

    /**
     * 校验accessKey创建的时候信息的正确性,并设置公司编号
     * @param accesskeyDTO
     * @throws ApiGateWayException 非空异常、编码不存在等多种异常
     */
    private void validateCreate(AccesskeyDTO accesskeyDTO) throws ApiGateWayException {
        if (accesskeyDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.ACCESSKEY_IS_EMPTY);
        }

        String keyName = accesskeyDTO.getName();
        String userCode = accesskeyDTO.getUserCode();

        if (StringUtils.isEmpty(keyName)){
            throw new ApiGateWayException(HttpErrorEnum.ACCESSKEY_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(userCode)){
            throw new ApiGateWayException(HttpErrorEnum.ACCESSKEY_USERCODE_EMPTY);
        }

        String companyCode = validateUserCompanyCode(userCode);
        validateKeyNameRepeat(keyName, companyCode);

        accesskeyDTO.setCompanyCode(companyCode);
    }

    /**
     * 公司的keyName-companyCode不能出现重复
     * @param keyName key的名字
     */
    private void validateKeyNameRepeat(String keyName, String companyCode) throws ApiGateWayException {
        AccesskeyExample accesskeyExample = new AccesskeyExample();
        AccesskeyExample.Criteria criteria = accesskeyExample.createCriteria();
        criteria.andNameEqualTo(keyName);
        criteria.andCompanyCodeEqualTo(companyCode);
        List<Accesskey> accesskeys = accesskeyMapper.selectByExample(accesskeyExample);

        if (!CollectionUtils.isEmpty(accesskeys)){
            throw new ApiGateWayException(HttpErrorEnum.KEY_NAME_IS_REPEAT);
        }
    }

    /**
     * 用户在创建accessKey之前需要校验公司信息是否合法
     * 校验用户对应的公司，如果公司是有效的，那么将公司code返回
     * @param userCode 用户code
     * @throws ApiGateWayException 当不存在的时候抛异常
     */
    private String validateUserCompanyCode(String userCode) throws ApiGateWayException {
        UserDTO userDTO = userService.getByUserCode(userCode);
        if (userDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.USER_CODE_IS_NOT_EXISTED);
        }else if (!userDTO.getActive()){
            throw new ApiGateWayException(HttpErrorEnum.USER_IS_NOT_AVAILABLE);
        }

        if (StringUtils.isEmpty(userDTO.getCompanyCode())){
            throw new ApiGateWayException(HttpErrorEnum.USER_COMPANY_IS_NOT_EXISTED);
        }else {
            String companyCode = userDTO.getCompanyCode();
            validateCompanyCode(companyCode);
            return companyCode;
        }
    }

    /**
     * 校验公司的可用性
     * @param companyCode
     * @throws ApiGateWayException
     */
    private void validateCompanyCode(String companyCode) throws ApiGateWayException {
        CompanyDTO companyDTO = companyService.getByCodeWithRedisAndDB(companyCode);
        if (companyDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.USER_COMPANY_IS_NOT_EXISTED);
        }else if (!companyDTO.getActive()){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_IS_NOT_AVAILABLE);
        }else {
            return;
        }
    }

    @Override
    public AuthenticateResultDTO authenticate(ApiRequestDTO apiRequestDTO) throws ApiGateWayException {
        AccesskeyDTO accesskeyDTO = validateAccessKeyToken(apiRequestDTO);

        CompanyDTO companyDTO = companyService.getByCodeWithRedisAndDB(accesskeyDTO.getCompanyCode());
        if (!companyDTO.getActive()){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_IS_NOT_AVAILABLE);
        }

        String privateSecret = accesskeyDTO.getPrivateSecret();

        checkSign(apiRequestDTO, accesskeyDTO.getSecret());

        Boolean aBoolean = JwtUtil.checkAppTokenExpired(apiRequestDTO.getKeyToken(), privateSecret);
        if (aBoolean){
            throw new ApiGateWayException(HttpErrorEnum.KEY_TOKEN_IS_EXPIRED);
        }

        String key = JwtUtil.getKey(apiRequestDTO.getKeyToken(), privateSecret);
        if (!apiRequestDTO.getKey().equals(key)){
            throw new ApiGateWayException(HttpErrorEnum.KEY_TOKEN_IS_NOT_RIGHT);
        }


        MatchApiDTO matchApiDTO = resourceService
                        .matchWithAnt(apiRequestDTO.getApiPath(), RequestMethod.valueOf(apiRequestDTO.getRequestMethod()));
        String resourceCode = matchApiDTO.getResourceCode();
        if (StringUtils.isEmpty(resourceCode)){
            log.info("api路径：" + apiRequestDTO.getApiPath());
            log.info("api方式：" + apiRequestDTO.getRequestMethod());
            throw new ApiGateWayException(HttpErrorEnum.API_PATH_NOT_EXIST);
        }

        // TODO: 2018/9/14 待优化 每个key 针对Resourcecode的额度 Redis 缓存 每天0点定时同步数据库 或者手动更新缓存 因为暂时没有额度需求所以延缓开发
        QuotaDTO quotaDTO = quotaService.checkQuota(key, resourceCode);

        AuthenticateResultDTO authenticateResultDTO = new AuthenticateResultDTO();
        authenticateResultDTO.setCompanyId(companyDTO.getId());
        authenticateResultDTO.setRequestId(matchApiDTO.getRequestId());
        authenticateResultDTO.setCompanyCode(companyDTO.getCompanyCode());
        authenticateResultDTO.setName(companyDTO.getName());
        authenticateResultDTO.setBusinessCode(companyDTO.getBusinessCode());
        authenticateResultDTO.setKey(key);
        authenticateResultDTO.setResourceCode(resourceCode);
        if (quotaDTO != null){
//            authenticateResultDTO.setKey(key);
//            authenticateResultDTO.setResourceCode(resourceCode);
            authenticateResultDTO.setCallback(CallBackConstants.CALLBACK);
        }else {
            authenticateResultDTO.setCallback(CallBackConstants.NOT_CALLBACK);
        }

        return authenticateResultDTO;
    }

    /**
     * 校验签名是否合法
     * 1.参数排序：因为参数是键值对的结构，所以按照参数的key进行排序，并用"&"连接
     * 2.参数排序后追加时间戳参数
     * 3.将上述字符串转换成大写
     * 4.进行md5加密，加密过程中使用加盐操作
     * 5.计算签名，然后进行比对，如果签名相同，那么签名就是合法的，否则抛异常
     * 6.如果是含有body体的，签名只计算body（json形式）的第一层结构
     *
     * @param apiRequestDTO
     * @param secret key密钥，在此作为盐使用
     * @throws ApiGateWayException 签名不合法
     */
    private void checkSign(ApiRequestDTO apiRequestDTO, String secret) throws ApiGateWayException {

        Map<String, Object> params = apiRequestDTO.getParams();

        List<String> keys = new ArrayList<>();
        params.forEach( (key, value) -> {
            keys.add(key);
        });

        Collections.sort(keys);

        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(key + "=" + params.get(key) + "&");
            log.info("值：" + params.get(key));
        }

        if (!StringUtils.isEmpty(apiRequestDTO.getBody())){
            String body = apiRequestDTO.getBody();
            JSONObject bodyObject = JSON.parseObject(body);
            Set<String> bodyKeySet = bodyObject.keySet();
            List<String> list = new ArrayList<>(bodyKeySet);
            Collections.sort(list);

            for (String bodyKey : list) {
                sb.append(bodyKey + "=" + bodyObject.get(bodyKey) + "&");
                log.info("值：" + bodyObject.get(bodyKey));
            }

            /*List<String> list = new ArrayList<>();
            char[] chars = body.toCharArray();
            for (char aChar : chars) {
                String s = String.valueOf(aChar);
                list.add(s);
            }

            Collections.sort(list);
            list.forEach(str->{
                sb.append(str);
            });*/
        }

        sb.append(apiRequestDTO.getReqTimestamp());

        String signServer = MD5Util.MD5(sb.toString().toUpperCase() + secret);

        if (!apiRequestDTO.getSign().equals(signServer)){
            log.info("签名明文：" + sb.toString().toUpperCase());
            log.info("服务器签名：" + signServer);
            log.info("客户端签名：" + apiRequestDTO.getSign());
            throw new ApiGateWayException(HttpErrorEnum.SIGN_NO_RIGHT);
        }
    }

    /**
     * 1.校验时间戳的可用性
     * 2.校验apiRequestDTO的有效性，如果是可用的那么返回accessKey对象
     * @param apiRequestDTO
     * @return
     * @throws ApiGateWayException
     */
    private AccesskeyDTO validateAccessKeyToken(ApiRequestDTO apiRequestDTO) throws ApiGateWayException {
        if (apiRequestDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.KEY_TOKEN_IS_EMPTY);
        }

        checkTimestamp(apiRequestDTO.getReqTimestamp());

        AccesskeyDTO accesskeyDTO = getByKeyAvailable(apiRequestDTO.getKey());
        if (accesskeyDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.KEY_IS_NOT_AVAILABLE);
        }

        return accesskeyDTO;
    }

    /**
     * 校验时间差，客户端时间与认证服务器时间差只有五分钟时间差
     * @param timestamp 客户端请求参数时间戳
     * @throws ApiGateWayException 时间戳不合法
     * @return
     */
    private void checkTimestamp(Long timestamp) throws ApiGateWayException {
        long l1 = System.currentTimeMillis();
        boolean b = (l1 - timestamp) < TimeStampConstants.CLIENT_REQUEST_OVERDUE;
        if (b){
            return;
        }else {
            throw new ApiGateWayException(HttpErrorEnum.TIMESTAMP_NO_RIGHT);
        }
    }

    /**
     * key查询一个有效的Accesskey对象
     * @param key
     * @return
     */
    private AccesskeyDTO getByKeyAvailable(String key) throws ApiGateWayException {
        AccesskeyDTO accesskeyDTO = getByKeyWithRedisAndDB(key);
        if (accesskeyDTO == null){
            return null;
        }else if (accesskeyDTO.getActive()){
            return accesskeyDTO;
        }else {
            return null;
        }
    }

    /**
     * 根据key 查询accessKey
     * @param key
     * @return 唯一一个Accesskey对象
     */
    private AccesskeyDTO getByKeyWithRedisAndDB(String key) throws ApiGateWayException {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(key.trim())){
            return null;
        }
        AccesskeyDTO accesskeyDTO = accesskeyCacheManage.get(key);
        if (accesskeyDTO != null){
            return accesskeyDTO;
        }

        AccesskeyExample accesskeyExample = new AccesskeyExample();
        AccesskeyExample.Criteria criteria = accesskeyExample.createCriteria();
        criteria.andKeyEqualTo(key);
        List<Accesskey> accesskeys = accesskeyMapper.selectByExample(accesskeyExample);

        if (CollectionUtils.isEmpty(accesskeys)){
            return null;
        }else if (accesskeys.size() > 1){
            throw new ApiGateWayException(HttpErrorEnum.KEY_QUERY_MANY_RESULTS);
        }else {
            Accesskey accesskey = accesskeys.get(0);
            accesskeyDTO = new AccesskeyDTO();
            BeanUtils.copyProperties(accesskey, accesskeyDTO);

            accesskeyCacheManage.putForever(accesskeyDTO);
            return accesskeyDTO;
        }
    }

    /**
     * ID查询
     * @param id
     * @return
     */
    private Accesskey getById(Integer id){
        if (id == null || id == 0){
            return null;
        }
        AccesskeyKey accesskeyKey = new AccesskeyKey();
        accesskeyKey.setId(id);
        Accesskey accesskey = accesskeyMapper.selectByPrimaryKey(accesskeyKey);

        return accesskey;
    }

    @Override
    public AccesskeyTokenDTO getNewToken(String keyName, Integer userId) throws ApiGateWayException {
        UserDTO userDTO = validateUserAvailable(userId);

        validateCompanyCode(userDTO.getCompanyCode());

        Accesskey accesskey = validateKeyAvailable(userDTO.getCompanyCode(), keyName);

        //重新生成新的privateSecrete，重新生成新的AccessKey，原来的accessKey作废，并从Redis中清除
        String privateSecret = UUIDUtil.generator() + RandomStrUtil.getRandomString(3);
        accesskey.setPrivateSecret(privateSecret);
        updateByIdRedisAndDB(accesskey);

        String keyToken = JwtUtil.generateAppToken(accesskey.getKey(), accesskey.getPrivateSecret(),
                                            Date.from(accesskey.getExpireTime().atZone(ZoneId.systemDefault())
                                                                      .toInstant()));

        AccesskeyTokenDTO accesskeyTokenDTO = new AccesskeyTokenDTO();
        BeanUtils.copyProperties(accesskey, accesskeyTokenDTO);
        accesskeyTokenDTO.setKeyToken(keyToken);

        return accesskeyTokenDTO;
    }

    /**
     * 更新key，更新数据库和Redis
     * @param accesskey
     */
    private void updateByIdRedisAndDB(Accesskey accesskey){
        accesskeyMapper.updateByPrimaryKeySelective(accesskey);
        AccesskeyDTO accesskeyDTO = new AccesskeyDTO();
        BeanUtils.copyProperties(accesskey, accesskeyDTO);
//        accesskeyCacheManage.remove(accesskeyDTO.getKey());
        accesskeyCacheManage.putForever(accesskeyDTO);
    }

    /**
     * 校验key的可用性
     * @param companyCode
     * @param keyName
     * @return
     * @throws ApiGateWayException
     */
    private Accesskey validateKeyAvailable(String companyCode, String keyName) throws ApiGateWayException {
        Accesskey accesskey = getByCompanyCodeAndName(companyCode, keyName);
        if (accesskey == null || !accesskey.getActive()){
            throw new ApiGateWayException(HttpErrorEnum.KEY_IS_NOT_AVAILABLE);
        }
        return accesskey;
    }

    /**
     * 条件查询，查询唯一性
     * @param companyCode
     * @param keyName
     * @return
     * @throws ApiGateWayException
     */
    private Accesskey getByCompanyCodeAndName(String companyCode, String keyName) throws ApiGateWayException {
        AccesskeyExample accesskeyExample = new AccesskeyExample();
        AccesskeyExample.Criteria criteria = accesskeyExample.createCriteria();
        criteria.andCompanyCodeEqualTo(companyCode);
        criteria.andNameEqualTo(keyName);
        List<Accesskey> accesskeys = accesskeyMapper.selectByExample(accesskeyExample);
        if (CollectionUtils.isEmpty(accesskeys)){
            return null;
        }else if (accesskeys.size() > 1){
            throw new ApiGateWayException(HttpErrorEnum.COMPANY_CODE_KEY_NAME_QUERY_MANY_RESULTS);
        }else {
            return accesskeys.get(0);
        }
    }

    /**
     * 校验用户ID的有效性,如果有效那么返回UserDTO
     * @param userId
     * @throws ApiGateWayException
     * @return 有效的用户信息
     */
    private UserDTO validateUserAvailable(Integer userId) throws ApiGateWayException {
        UserDTO userDTO = userService.getById(userId);
        if (userDTO == null || !userDTO.getActive()){
            throw new ApiGateWayException(HttpErrorEnum.USER_IS_NOT_AVAILABLE);
        }else {
            return userDTO;
        }
    }


    /**
     * 插入到数据库中，并缓存Redis
     * @param accesskeyDTO
     * @return
     */
    private int insertSelectiveRedisAndDB(AccesskeyDTO accesskeyDTO) {
        Accesskey accesskey = new Accesskey();
        BeanUtils.copyProperties(accesskeyDTO, accesskey);
        int i = accesskeyMapper.insertSelective(accesskey);
        AccesskeyKey accesskeyKey = new AccesskeyKey();
        accesskeyKey.setId(i);
        Accesskey tmp = accesskeyMapper.selectByPrimaryKey(accesskeyKey);
        BeanUtils.copyProperties(tmp, accesskeyDTO);
        accesskeyCacheManage.putForever(accesskeyDTO);
        return i;
    }

    @Override
    public List<AccesskeyDTO> getAllAvailable() {
        AccesskeyExample accesskeyExample = new AccesskeyExample();
        AccesskeyExample.Criteria criteria = accesskeyExample.createCriteria();
        criteria.andActiveEqualTo(true);
        List<Accesskey> accesskeys = accesskeyMapper.selectByExample(accesskeyExample);
        List<AccesskeyDTO> list = new ArrayList<>();
        for (Accesskey accesskey : accesskeys) {
            AccesskeyDTO accesskeyDTO = new AccesskeyDTO();
            BeanUtils.copyProperties(accesskey, accesskeyDTO);
            list.add(accesskeyDTO);
        }
        return list;
    }
}
