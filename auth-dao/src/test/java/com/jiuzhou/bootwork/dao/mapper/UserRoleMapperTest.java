package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.mapper.base.BaseTest;
import com.jiuzhou.bootwork.dao.model.UserRole;
import com.jiuzhou.bootwork.dao.model.UserRoleKey;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/25
 */
public class UserRoleMapperTest extends BaseTest{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void updateRemainderById() {

        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setId(1);

        UserRole userRole = userRoleMapper.selectByPrimaryKey(userRoleKey);
        System.out.println(userRole);

        /*
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            final int index = i;
            //            try {
            //                Thread.sleep(index * 1000);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    int i = userRoleMapper.updateRemainderById(1L);
                    System.out.println(i);
                }
            });
        }
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}