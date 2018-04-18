package com.jiuzhou.bootwork.service.dto;

import com.jiuzhou.bootwork.dao.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Deprecated
    private List<String> roleNames;

    private Map<String, RoleDto> roleDtoMap;

    private static final long serialVersionUID = 1L;

}