package com.jiuzhou.bootwork.basecontroller;

import com.jiuzhou.bootwork.excep.HttpErrorEnum;
import com.jiuzhou.bootwork.exception.ApiException;
import com.jiuzhou.bootwork.result.ErrorResult;
import com.jiuzhou.bootwork.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/05/11
 * 这是一个controller的基类，继承这个基类可以做到自动处理controller层的异常
 */
@Slf4j
@Component
public class BaseController {


    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 处理所有controller层自定义的apiException异常
     * @param apiException controller层的所有apiException异常
     * @param response
     * @return
     */
    @ExceptionHandler({ ApiException.class })
    public Result handlerException(ApiException apiException, HttpServletResponse response) {

        log.error(apiException.getMessage());

        response.setStatus(apiException.getHttpError().getHttpStatus().value());

        Result result = new ErrorResult(apiException.getHttpError());

        return result;
    }

    /**
     * （此方法尚未验证实用性）
     * 处理参数错误异常
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerException(MethodArgumentNotValidException e, HttpServletResponse response) {

        log.error(e.getMessage(), e.getStackTrace());

        response.setStatus(500);

        Result result = new ErrorResult("", e.getBindingResult().getFieldError().getDefaultMessage());

        return result;
    }

    /**
     * 处理其他异常
     * @param e
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e, HttpServletResponse response) {

        log.error(e.getMessage(), e.getStackTrace());

        response.setStatus(500);

        Result result = new ErrorResult(HttpErrorEnum.SYS_ERROR);

        return result;
    }
}
