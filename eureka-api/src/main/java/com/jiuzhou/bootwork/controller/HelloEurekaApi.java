package com.jiuzhou.bootwork.controller;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.result.Result;
import com.jiuzhou.bootwork.service.AuthServerClient;
import com.jiuzhou.bootwork.service.dto.UpdateQuotaDTO;
import com.jiuzhou.bootwork.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.logging.Logger;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/16
 */
@RestController
@RequestMapping(value = "/api/v1/schedule")
@Slf4j
@Api(value = "eureka-api 第一个服务接口")
public class HelloEurekaApi {

    private final Logger logger = Logger.getLogger(HelloEurekaApi.class.getName());

    @Autowired
    private AuthServerClient authServerClient;

    @GetMapping(value = "/get")
    @ApiOperation(value = "get 方法示例")
    public Result<ProductVO> get(@RequestParam Long id){
        //公司信息（一般api服务自己用此信息，处理schema等数据库业务）
        String companyId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                        .getHeader("companyId");
        //本次请求是否需要回调 1：需要回调 2：不需要回调
        String callback = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                        .getHeader("callback");
        int i = Integer.parseInt(callback);
        String key = "";
        String resourceCode = "";

        try {
        if (i == 1){
            //请求额度回调参数（请求者身份）
            key = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                            .getHeader("key");
            //请求额度回调参数（资源唯一标识）
            resourceCode = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                            .getHeader("resourceCode");
            log.info("请求者身份key：" + URLDecoder.decode(key, "UTF-8"));
            log.info("请求资源标识：" + URLDecoder.decode(resourceCode, "UTF-8"));
        }
            log.info("公司ID：" + URLDecoder.decode(companyId, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Result<ProductVO> result = null;
        ProductVO productVO = new ProductVO();
        productVO.setId(id);
        productVO.setName("华为 P20 Pro");
        productVO.setPrice(new BigDecimal(9999));
        result = Result.buildSuccess(productVO);

        if (i == 1){
            //额度减少12次为例
            UpdateQuotaDTO updateQuotaDTO = new UpdateQuotaDTO();

            updateQuotaDTO.setKey(key);
            updateQuotaDTO.setResourceCode(resourceCode);
            updateQuotaDTO.setUpdateQuota(-12L);
            authServerClient.updateQuota(updateQuotaDTO);
        }
        return result;
    }

    @PostMapping(value = "/post")
    @ApiOperation(value = "post 方法")
    public Result<String> post(@RequestBody ProductVO productVO){
        Result result = new Result<>();
        result = Result.buildSuccess("你真棒！");
        return result;
    }

    @GetMapping(value = "")
    public Result<String> hello(HttpServletRequest request) /*throws Exception*/ {
        String requestId = request.getHeader("requestId");
        log.info("接到请求：" + JSON.toJSONString(requestId));
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        throw new Exception("异常");
        Result<String> result = new Result<>();
        result.setData(requestId);
        return result;
    }

}
