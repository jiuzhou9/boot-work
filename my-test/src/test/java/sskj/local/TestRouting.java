package sskj.local;

import com.alibaba.fastjson.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/09
 */
public class TestRouting {

    @Test
    public void test() {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                                              "{\n  \"globalSetting\": {\n    \"branchId\": \"DEPPON\",\n    "
                                                              + "\"deptId\": \"DEPPON-HZ\",\n    \"mapVendor\": "
                                                              + "\"GD\",\n    \"matrixDatabase\": \"matrix\",\n    "
                                                              + "\"matrixSchema\": \"public\"\n  },\n  \"requestId\":"
                                                              + " \"◊home◊unicorn◊engine◊output◊public◊DEPPON◊DEPPON-HZ◊2018◊9◊18◊201809180003◊\",\n  \"dataType\": \"csv\",\n  \"output\": \"false\",\n  \"vehicleGrades\": [\n    \"2\",\n    \"2\"\n  ],\n  \"locations\": [\n    {\n      \"locId\": \"H7868\",\n      \"longitude\": 120.280303,\n      \"latitude\": 30.3658\n    },\n    {\n      \"locId\": \"W3100020655\",\n      \"longitude\": 120.368944,\n      \"latitude\": 30.334709\n    }\n  ],\n  \"callBackURL\": \"http://10.1.1.8:8383/api/v2/data/matrix\"\n}");
        Request request = new Request.Builder().url("http://127.0.0.1:8099/api/v1/matrix").post(body)
                        .addHeader("Cache-Control", "no-cache").addHeader("Content-Type", "application/json")
                        .addHeader("Postman-Token",
                                   "3f4cc369-840d-4ea7-9d58-c64cef7bb1f9,14885d7c-1604-4e26-8d3c-f110f176a07a")
                        .addHeader("cache-control", "no-cache").build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(JSON.toJSONString(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
