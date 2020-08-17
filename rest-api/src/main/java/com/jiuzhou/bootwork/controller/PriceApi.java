package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.basecontroller.BaseController;
import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.exception.ApiException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/01
 */
@RestController
@RequestMapping("/price")
public class PriceApi extends BaseController {

    @GetMapping("")
    public List<RemotePriceResp> hello(@RequestParam String salesCode, @RequestParam String priceType){
        RemotePriceResp remotePriceResp = new RemotePriceResp();
        remotePriceResp.setPrice("12");
        remotePriceResp.setProductCode("12");
        remotePriceResp.setProductName("苹果");

        RemotePriceResp remotePriceResp1 = new RemotePriceResp();
        remotePriceResp1.setPrice("121");
        remotePriceResp1.setProductCode("121");
        remotePriceResp1.setProductName("苹果1");
        List<RemotePriceResp> list = new ArrayList<>();
        list.add(remotePriceResp);
        list.add(remotePriceResp1);
        return list;
    }

    @GetMapping(value = "test-exception")
    public String testException() throws ApiException {
        throw new ApiException(HttpErrorEnum.USER_LOGIN_IS_ERROR);
    }
}

@Data
class RemotePriceResp {
    /*产品编码*/
    private String productCode;
    /*产品描述*/
    private String productName;
    /*价格*/
    private String price;
}

@Data
class SalePriceReq {
    /*客户编码*/
    private String salesCode;
    /*价格模式*/
    private String priceType;
}
