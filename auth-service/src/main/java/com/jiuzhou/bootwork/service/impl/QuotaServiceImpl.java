package com.jiuzhou.bootwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.cache.QuotaCacheManager;
import com.jiuzhou.bootwork.cache.cdto.QuotaCacheDTO;
import com.jiuzhou.bootwork.common.QuotaConstants;
import com.jiuzhou.bootwork.dao.mapper.QuotaMapper;
import com.jiuzhou.bootwork.dao.model.Quota;
import com.jiuzhou.bootwork.dao.model.QuotaExample;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.service.QuotaService;
import com.jiuzhou.bootwork.service.dto.QuotaDTO;
import com.jiuzhou.bootwork.service.dto.UpdateQuotaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Service
@Slf4j
public class QuotaServiceImpl implements QuotaService {

    @Autowired
    private QuotaMapper quotaMapper;

    @Autowired
    private QuotaCacheManager quotaCacheManager;

    @Override
    public QuotaDTO getByKeyAndResourceCodeWithRedisAndDB(String key, String resourceCode) throws ApiGateWayException {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(key.trim()) || StringUtils.isEmpty(resourceCode) || StringUtils.isEmpty(resourceCode.trim())){
            throw new ApiGateWayException(HttpErrorEnum.QUOTA_QUERY_KEY_RESOURCECODE_NOT_EMPTY);
        }

        QuotaCacheDTO quotaCacheDTO = quotaCacheManager.get(key + "_" + resourceCode);
        if (quotaCacheDTO != null){
            return quotaCacheDTO.getQuotaDTO();
        }

        QuotaExample quotaExample = new QuotaExample();
        QuotaExample.Criteria criteria = quotaExample.createCriteria();
        criteria.andKeyEqualTo(key);
        criteria.andResourceCodeEqualTo(resourceCode);
        List<Quota> quotas = quotaMapper.selectByExample(quotaExample);

        if (CollectionUtils.isEmpty(quotas)){
            return null;
        }else if (quotas.size() > 1){
            throw new ApiGateWayException(HttpErrorEnum.QUOTA_KEY_RESOURCECODE_QUERY_MANY_RESULTS);
        }else {
            if (quotas.get(0).getActive()){
                QuotaDTO quotaDTO = new QuotaDTO();
                BeanUtils.copyProperties(quotas.get(0), quotaDTO);
                return quotaDTO;
            }else {
                return null;
            }
        }
    }

    @Override
    public QuotaDTO checkQuota(String key, String resourceCode) throws ApiGateWayException {
        QuotaDTO quotaDTO = getByKeyAndResourceCodeWithRedisAndDB(key, resourceCode);
        if (quotaDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.QUOTA_IS_NOT_EXIST);
        }

        Integer type = quotaDTO.getType();
        if (type.equals(QuotaConstants.NO_CALCULATION)){
            return null;

        }else if (type.equals(QuotaConstants.ACCORDING_TO_THE_TIME)){
            LocalDateTime endTime = quotaDTO.getEndTime();
            boolean after = endTime.isAfter(LocalDateTime.now());
            if (after){
                return null;
            }else {
                throw new ApiGateWayException(HttpErrorEnum.KEY_RESOURCECODE_QUOTA_IS_EXPIRED);
            }

        }else if (type.equals(QuotaConstants.ACCORDING_TO_THE_NUMBER_OF_TIMES) || type.equals(QuotaConstants.ACCORDING_TO_THE_NUMBER_OF_CARS)){
            Long quota = quotaDTO.getQuota();
            if (quota.compareTo(0L) > 0){
                return quotaDTO;
            }else {
                log.info("调用额度：" + JSON.toJSONString(quotaDTO));
                throw new ApiGateWayException(HttpErrorEnum.KEY_RESOURCECODE_TIMES_QUOTA_IS_NULL);
            }

        }else {
            throw new ApiGateWayException(HttpErrorEnum.QUOTA_TYPE_IS_NOT_SUPPORT);
        }
    }


    @Override
    public List<QuotaDTO> getAllAvailable() {
        QuotaExample quotaExample = new QuotaExample();
        List<Quota> quotas = quotaMapper.selectByExample(quotaExample);
        List<QuotaDTO> list = new ArrayList<>();
        for (Quota quota : quotas) {
            QuotaDTO quotaDTO = new QuotaDTO();
            BeanUtils.copyProperties(quota, quotaDTO);
            list.add(quotaDTO);
        }
        return list;
    }

    @Override
    public void updateRedisQuota(UpdateQuotaDTO updateQuotaDTO) throws ApiGateWayException {
        String key = updateQuotaDTO.getKey();
        String resourceCode = updateQuotaDTO.getResourceCode();

        QuotaDTO quotaDTO = getByKeyAndResourceCodeWithRedisAndDB(key, resourceCode);

        Long updateQuota = updateQuotaDTO.getUpdateQuota();
        long l = quotaDTO.getQuota() + updateQuota;
        quotaDTO.setQuota(l);

        QuotaCacheDTO quotaCacheDTO = new QuotaCacheDTO();
        quotaCacheDTO.setQuotaKey(quotaDTO.getKey() + "_" + quotaDTO.getResourceCode());
        quotaCacheDTO.setQuotaDTO(quotaDTO);
        quotaCacheManager.putForever(quotaCacheDTO);
    }

}
