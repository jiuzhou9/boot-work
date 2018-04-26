package com.jiuzhou.bootwork.dao.mapper;

import com.jiuzhou.bootwork.dao.mapper.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/25
 */
public class UserRoleMapperTest extends BaseTest{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void updateRemainderById() {

        int i = userRoleMapper.updateRemainderById(1L);
        System.out.println(i);

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