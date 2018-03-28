package com.jiuzhou.bootwork.excep;

import org.springframework.http.HttpStatus;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
public enum HttpErrorEnum implements HttpError {
    USERNAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010001", "用户名已经存在"),
    MOBILE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010002", "手机号码已经存在"),
    USER_PARAMETERS_ARE_EMPTY(HttpStatus.BAD_REQUEST,"10010003", "用户参数为空"),
    MOBILE_PARAMETER_ARE_EMPTY(HttpStatus.BAD_REQUEST, "10010004", "手机参数为空"),
    USERNAME_PARAMETER_ARE_EMPTY(HttpStatus.BAD_REQUEST, "10010005", "用户名参数为空"),
    PASSWORD_PARAMETER_ARE_EMPTY(HttpStatus.BAD_REQUEST, "10010006", "密码参数为空"),
    USERNAME_PARAMETER_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010007", "用户名参数查询到多条数据结果"),
    MOBILE_PARAMETER_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010008", "手机号参数查询到多条数据结果"),
    ;

    private HttpStatus httpStatus;
    private String code;
    private String message;

    HttpErrorEnum(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static HttpError getError(String msg){
        for (HttpErrorEnum httpErrorEnum:HttpErrorEnum.values()){
            if (httpErrorEnum.getMessage().equals(msg)){
                return httpErrorEnum;
            }
        }
        return null;
    }
}
