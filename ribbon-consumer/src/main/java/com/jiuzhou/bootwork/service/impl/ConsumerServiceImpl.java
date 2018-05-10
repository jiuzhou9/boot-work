package com.jiuzhou.bootwork.service.impl;

import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.ConsumerService;
import com.jiuzhou.bootwork.service.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/02/22
 */
@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    RestTemplate restTemplate;

    //异常情况处理
//    @HystrixCommand(fallbackMethod = "error")
    @Override
    public Result<ProductDTO> getProductInfo(Long productId) {
//        RestTemplate restTemplate = new RestTemplate();
//        String body = restTemplate.getForEntity("http://EUREKA-API/api/v1/product/info", String.class).getBody();
        ResponseEntity<Result> responseEntity = restTemplate
                        .getForEntity("http://EUREKA-API/api/v1/product/info/"+productId, Result.class);
        Result<ProductDTO> result = responseEntity.getBody();
        return result;
    }

    public Result<ProductDTO> error(){
        Result result = new Result();
        //失败 fixme
        return result;
    }

}
