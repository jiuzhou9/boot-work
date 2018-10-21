package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.cache.ResourceCacheManager;
import com.jiuzhou.bootwork.cache.cdto.ResourceCacheDTO;
import com.jiuzhou.bootwork.common.StringUtilsSSKJ;
import com.jiuzhou.bootwork.common.UUIDUtil;
import com.jiuzhou.bootwork.dao.mapper.ResourceMapper;
import com.jiuzhou.bootwork.dao.model.Resource;
import com.jiuzhou.bootwork.dao.model.ResourceExample;
import com.jiuzhou.bootwork.excep.ApiGateWayException;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.service.RequestService;
import com.jiuzhou.bootwork.service.ResourceService;
import com.jiuzhou.bootwork.service.dto.MatchApiDTO;
import com.jiuzhou.bootwork.service.dto.ResourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 键：资源路径 + "," + 请求方式
     * 值：资源code
     */
    @Deprecated
    private Map<String, String> resourcesMap;

    /**
     * 键：资源路径 + "," + 请求方式
     * 值：资源对象Resource
     */
    private Map<String, ResourceDTO> resMap;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ResourceCacheManager resourceCacheManager;

    /**
     * 加载资源集合，集合中的资源都是有效的
     */
    @Deprecated
    private void loadResourcesMap(){
        List<ResourceDTO> resources = getAvailable();

        if (CollectionUtils.isEmpty(resources)){
            return;
        }

        resourcesMap = new HashMap<>();
        resources.forEach( resource -> {
            resourcesMap.put("/" + StringUtilsSSKJ.get2(resource.getType()) + resource.getUri() + "," + resource.getAction(), resource.getResourceCode());
        });

        resMap = new HashMap<>();
        resources.forEach( resource -> {
            resMap.put("/" + StringUtilsSSKJ.get2(resource.getType()) + resource.getUri() + "," + resource.getAction(), resource);
        });

    }



    /**
     * 完全匹配 根据请求资源获取相应的角色列表
     *
     * 请求映射器
     * @param reqApi 请求的api路径
     * @param method 请求方式
     * @param count 请求URL中的参数个数
     * @return resourcesMap值，即资源编码
     */
    @Deprecated
    private String getAttributesCompletelyEquals(String reqApi, String method, int count){

        reqApi = removeParameter(reqApi, count);

        String reqApiWithMethod = reqApi + "," + method;

        if (CollectionUtils.isEmpty(resourcesMap)) {
            loadResourcesMap();
            if (CollectionUtils.isEmpty(resourcesMap)){
                return null;
            }
        }

        String resUrlWithMethod;
        String resUrlWithMethodOrigin;

        for (Iterator<String> iter = resourcesMap.keySet().iterator(); iter.hasNext(); ) {
            resUrlWithMethod = iter.next();
            resUrlWithMethodOrigin = resUrlWithMethod;

            if (count > 0){
                String[] resourceArray = resUrlWithMethod.split(",");

                resUrlWithMethod = removeParameter(resUrlWithMethod, count) + "," + resourceArray[1];
            }
            if (resUrlWithMethod.equals(reqApiWithMethod)) {
                return resourcesMap.get(resUrlWithMethodOrigin);
            }
        }
        return null;
    }

    /**
     * api路径去参数化
     * @param apiPathWithParameter 带有参数的api路径
     * @param count 参数个数
     * @return 去参数后的api路径
     */
    @Deprecated
    private String removeParameter(String apiPathWithParameter, int count){
        String[] split = apiPathWithParameter.split("/");
        StringBuilder apiPath = new StringBuilder();

        for (int i = 0; i < split.length - count; i++) {
            if (i != 0){
                apiPath.append( "/" + split[i]);
            }
        }

        return apiPath.toString();
    }



    /**
     * 查询所有有效的资源
     * @return
     */
    @Override
    public List<ResourceDTO> getAvailable(){
        ResourceExample resourceExample = new ResourceExample();
        ResourceExample.Criteria criteria = resourceExample.createCriteria();
        criteria.andActiveEqualTo(true);
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        List<ResourceDTO> list = new ArrayList<>();
        resources.forEach(resource -> {
            ResourceDTO resourceDTO = new ResourceDTO();
            BeanUtils.copyProperties(resource, resourceDTO);
            list.add(resourceDTO);
        });
        return list;
    }

    @Override
    public String match(String reqApiPath, RequestMethod requestMethod) {
        String resourceCode = "";
        if (requestMethod.equals(RequestMethod.DELETE) || requestMethod.equals(RequestMethod.GET) || requestMethod.equals(RequestMethod.PUT)){

            String[] split = reqApiPath.split("/");
            for (int i = 0; i < split.length - 1; i++){
                resourceCode = getAttributesCompletelyEquals(reqApiPath, requestMethod.name(), i);
                if (!StringUtils.isEmpty(resourceCode)){
                    return resourceCode;
                }
            }
        }else {
            resourceCode = getAttributesCompletelyEquals(reqApiPath, requestMethod.name(), 0);
        }
        return resourceCode;
    }

    @Override
    public MatchApiDTO matchWithAnt(String reqApiPath, RequestMethod requestMethod) throws ApiGateWayException {
        /*if (CollectionUtils.isEmpty(resMap)) {
            loadResourcesMap();
            if (CollectionUtils.isEmpty(resMap)){
                return null;
            }
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        Set<String> set = resMap.keySet();

        for (String apiUrl : set) {
            log.info("api 资源：" + apiUrl);
            //mapping映射 和   请求方式比较
            boolean match = antPathMatcher.match(apiUrl.split(",")[0], reqApiPath)
                            && apiUrl.split(",")[1].equals(requestMethod.name());
            if (match){
                Resource resource = resMap.get(apiUrl);
                String resourceCode = resource.getResourceCode();
                MatchApiDTO matchApiDTO = new MatchApiDTO();
                matchApiDTO.setResourceCode(resourceCode);
                matchApiDTO.setRequestId(requestService.getRequestId(resource.getPre()));
                return matchApiDTO;
            }

        }*/

        String serverName = StringUtilsSSKJ.getServerNameByApiPath(reqApiPath);
        ResourceCacheDTO resourceCacheDTO = resourceCacheManager.get(serverName);

        List<ResourceDTO> list = resourceCacheDTO.getList();
        if (CollectionUtils.isEmpty(list)){
            //查库，更新Redis 实属不便利，因此暂不支持，此处我会设置指定的方法在项目启动就立即执行/服务注册、更新的时候也将更新，即使实现也将会耗费大量资源得不偿失
            throw new ApiGateWayException(HttpErrorEnum.API_PATH_NOT_EXIST);
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (ResourceDTO resourceDTO : list) {
            String s = "/" + StringUtilsSSKJ.get2(resourceDTO.getType()) + resourceDTO.getUri();
            boolean match = antPathMatcher
                            .match(s, reqApiPath)
                            && resourceDTO.getAction().equals(requestMethod.name());
            if (match){
                MatchApiDTO matchApiDTO = new MatchApiDTO();
                matchApiDTO.setResourceCode(resourceDTO.getResourceCode());
                String pre = StringUtilsSSKJ.get5(resourceDTO.getType());
                matchApiDTO.setRequestId(requestService.getRequestId(""));
                return matchApiDTO;
            }
        }
        throw new ApiGateWayException(HttpErrorEnum.API_PATH_NOT_EXIST);
    }

    @Override
    public int createResource(ResourceDTO resourceDTO) throws ApiGateWayException {
        if (resourceDTO == null){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }

        String action = resourceDTO.getAction();
        String uri = resourceDTO.getUri();
        String type = resourceDTO.getType();
        Boolean active = resourceDTO.getActive();
        String comments = resourceDTO.getComments();
        String name = resourceDTO.getName();

        if (StringUtils.isEmpty(action) && !StringUtils.isEmpty(HttpMethod.resolve(action))){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }else if (StringUtils.isEmpty(uri)){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }else if (StringUtils.isEmpty(type) && StringUtilsSSKJ.validateResourceType(type)){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }else if (StringUtils.isEmpty(comments)){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }else if (StringUtils.isEmpty(name)){
            throw new ApiGateWayException(HttpErrorEnum.PARAM_NOT_RIGHT);
        }

        resourceDTO.setResourceCode(UUIDUtil.generator());

        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDTO, resource);
        int i = resourceMapper.insertSelective(resource);

        ResourceCacheDTO resourceCacheDTO = new ResourceCacheDTO();
        resourceCacheDTO.setServerName(StringUtilsSSKJ.get2(resourceDTO.getType()));

        resourceCacheManager.putForever(resourceCacheDTO);
        return i;
    }

    @Override
    public List<ResourceCacheDTO> getResourcesToCache() {
        List<ResourceDTO> resourceDTOS = getAvailable();
        Set<String> serverNames = new HashSet<>();
        for (ResourceDTO resourceDTO : resourceDTOS) {
            serverNames.add(StringUtilsSSKJ.get2(resourceDTO.getType()));
        }

        List<ResourceCacheDTO> resourceCacheDTOS = new ArrayList<>();
        serverNames.forEach(serverName->{
            ResourceCacheDTO resourceCacheDTO = new ResourceCacheDTO();
            resourceCacheDTO.setServerName(serverName);
            resourceCacheDTO.setList(new ArrayList<>());
            resourceCacheDTOS.add(resourceCacheDTO);
        });

        for (ResourceDTO resourceDTO : resourceDTOS) {
            for (ResourceCacheDTO resourceCacheDTO : resourceCacheDTOS) {
                if (StringUtilsSSKJ.get2(resourceDTO.getType()).equals(resourceCacheDTO.getServerName())){
                    resourceCacheDTO.getList().add(resourceDTO);
                }
            }
        }
        return resourceCacheDTOS;
    }
}
