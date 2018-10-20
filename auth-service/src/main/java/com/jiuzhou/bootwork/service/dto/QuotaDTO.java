package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/19
 */
@Data
public class QuotaDTO {
    private Integer id;

    private String key;

    private String resourceCode;

    private Long quota;

    private Long dailyquota;

    private Long concurrencyLimit;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer type;

    private String companyCode;

    private String userCode;

    private Boolean active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
