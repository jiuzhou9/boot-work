package com.jiuzhou.bootwork.security;

import com.jiuzhou.bootwork.service.UserService;
import com.jiuzhou.bootwork.service.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
@Service
@Slf4j
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = null;
        try {
            userDto = userService.selectOneByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDto == null){
            throw new UsernameNotFoundException(String.format("用户名未查询到%s", username));
        }else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new User(username, userDto.getPassword(), authorities);
        }
    }
}
