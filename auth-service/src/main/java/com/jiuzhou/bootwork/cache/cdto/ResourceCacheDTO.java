package com.jiuzhou.bootwork.cache.cdto;

import com.jiuzhou.bootwork.service.dto.ResourceDTO;
import lombok.Data;

import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/15
 */
@Data
public class ResourceCacheDTO {
    /**
     * 服务的ServerName
     */
    private String serverName;

    private List<ResourceDTO> list;
}
