package com.jiuzhou.bootwork.service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    private String username;

    private String nickName;

    private String password;

    private String email;

    private String address;

    private String mobile;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<String> roleNames;

    private static final long serialVersionUID = 1L;

}