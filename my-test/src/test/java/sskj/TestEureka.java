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
        String urlStr = "http://localhost:15102/eureka-api/api/v1/example/get";

        Map<String, String> headers = RequestTool.buildHeaders("BZNTKomAhZwY3EU9iD",
                                                               "eyJhbGciOiJIUzUxMiJ9.eyJhcHBuYW1lIjoi5rWL6K-VQVBQIiwiZXhwIjoxNTI1MTAzOTI4fQ.eTDzilG7N1b_vN4F7zrWneppRuxTDCoPWwv_vlYjshb3iaRDeZ2f4h0XVFyejEdPa_UkT1rXPWwO_OuQcZSGWg", null);

        String responseEntity = RequestTool.getRequest(headers, urlStr, HttpMethod.GET);

        System.out.println(responseEntity);
    }
}
