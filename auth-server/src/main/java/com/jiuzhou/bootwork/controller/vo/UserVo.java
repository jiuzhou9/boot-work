package com.jiuzhou.bootwork.controller.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UserVo {

    private String username;

    private String nickName;

    private String password;

    private String email;

    private String address;

    private String mobile;

}