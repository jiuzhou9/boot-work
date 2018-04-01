package com.jiuzhou.bootwork.teststring;

import com.sun.xml.internal.fastinfoset.stax.factory.StAXOutputFactory;
import junit.framework.TestCase;
import org.springframework.util.StringUtils;

import javax.xml.bind.SchemaOutputResolver;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/07
 */
public class TestString extends TestCase{

    public void test_split(){
        String url = "/eureka-api/api/v1/hello";
        String[] split1 = url.split("/");
        System.out.println(split1[1]);

        String resource;
        for (int i = 2; i < split1.length; i++){
            resource = "/" + split1[i];
        }

        String s = "srn:unicorn:::api";
        String[] split = s.split(":");
        System.out.println(split.length);

        int i = StringUtils.countOccurrencesOf(s, ":");
        System.out.println(i);
    }
}
