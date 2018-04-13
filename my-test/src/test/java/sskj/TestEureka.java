package sskj;

import com.jiuzhou.bootwork.App;
import com.jiuzhou.bootwork.testrest.RequestTool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/04/13
 */
@Slf4j
@SpringBootTest(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEureka {
    /**
     * http 请求rest api 接口 此test 值能测试api 不能测试zuul
     */
    @Test
    //    @Ignore
    public void test_eureka_api(){
        String urlStr = "http://localhost:15102/eureka-api/api/v1/example/get?id=12";

        Map<String, String> headers = RequestTool.buildHeaders("T4NPJ7UxsIjzszyS0g",
                                                               "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoiYWJj55qEQVBQIiwiZXhwIjoxNTI2MTkwODg3LCJ1c2VybmFtZSI6ImFiYyJ9.UjRIkMEVzh2jwhnXJlpfCL9fHmgw4Tuu8NhVTVvVokML7dI7O5XGJqiFY8_zC4q37HHZNNxw0u7kJEwF5EFaPg", null);

        String responseEntity = RequestTool.getRequest(headers, urlStr, HttpMethod.GET);

        System.out.println(responseEntity);
    }
}
