package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyDTO {

    private Integer id;

    private String companyCode;

    private String businessCode;

    private String name;

    private String businessLicense;

    private String fax;

    private String address;

    private String zipcode;

    private String website;

    private String businessLicensePath;

    private String parentCompanyCode;

    private Boolean active;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}