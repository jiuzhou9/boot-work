package com.jiuzhou.bootwork.testresources;

import junit.framework.TestCase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2019/03/25
 */
public class ResourceUtils extends TestCase{

    public void test(){
        get();
    }

    /**
     * 获取resources 目录下的文件
     */
    public static void get(){
        Resource resource = new ClassPathResource("application.yml");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean exists = file.exists();
        System.out.println(exists);
    }
}
