package com.jiuzhou.bootwork.excep;

import org.springframework.http.HttpStatus;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/03/28
 */
public enum HttpErrorEnum implements HttpError {




//    信息维护调用相关的异常提示：
    USERNAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010001", "用户名已经存在"),
    USER_MOBILE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010002", "手机号码已经存在"),
    USER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010003", "用户参数为空"),
    USER_MOBILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010004", "手机参数为空"),
    USER_USERNAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010005", "用户名参数为空"),
    USER_PASSWORD_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010006", "密码参数为空"),
    USERNAME_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010007", "用户名参数查询到多条数据结果"),
    QUOTA_QUERY_KEY_RESOURCECODE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "10010008", "额度精准查询key,资源code不得为空"),
    KEY_NAME_IS_REPEAT(HttpStatus.BAD_REQUEST, "10010009", "key名字在当前公司已重复"),
    COMPANY_CODE_KEY_NAME_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010010", "公司code、keyName查询到多条数据结果"),

    COMPANY_BUSINESSLICENSE_IS_EMPTY(HttpStatus.BAD_REQUEST,"10010011", "公司营业执照编号为空"),
    USER_IS_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "10010012", "用户存在，但是无效用户"),
    QUOTA_TYPE_IS_NOT_SUPPORT(HttpStatus.BAD_REQUEST,"10010013", "额度计费不支持"),
    QUOTA_KEY_RESOURCECODE_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR, "10010014", "额度查询key,资源code查询到多条数据结果"),
    PARAM_NOT_RIGHT(HttpStatus.BAD_REQUEST, "10010015", "参数不正确请核实参数"),
    ACCESSKEY_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010016", "AccesskeyDTO参数为空"),
    KEY_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010017", "key参数查询到多条accessKey数据结果"),
    COMPANY_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST,"10010018", "公司名称为空"),
    QUOTA_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010019", "额度信息不存在"),
    APP_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "10010020", "APP更新失败"),

    //10010020-10010030
    USER_CODE_IS_NOT_EXISTED(HttpStatus.BAD_REQUEST,"10010031", "用户code查询不存在"),
    USER_EMAIL_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010032", "用户邮箱为空"),
    USER_EMAIL_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010033", "用户邮箱查询到多条数据结果"),
    USER_EMAIL_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010034", "用户邮箱已经存在"),
    USER_REGISTER_TYPE_IS_ERROR(HttpStatus.BAD_REQUEST,"10010035", "用户注册方式错误"),
    USER_MOBILE_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010036", "用户手机查询到多条数据结果"),
    USER_CODE_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010038", "用户code查询到多条数据结果"),
    SYS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10010039", "系统错误"),
    USER_MOBILE_EMAIL_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010040", "用户手机号、邮箱均为空"),

    USER_LOGIN_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010041", "用户登录信息为空"),
    USER_LOGIN_IS_ERROR(HttpStatus.BAD_REQUEST, "10010042", "用户登录认证失败，请重新登录"),
    COMPANY_CODE_QUERY_MANY_RESULTS(HttpStatus.INTERNAL_SERVER_ERROR,"10010043", "公司code查询到多条数据结果"),
    COMPANY_CODE_IS_NOT_EXISTED(HttpStatus.BAD_REQUEST,"10010044", "公司code不存在"),
    COMPANY_CODE_IS_EMPTY(HttpStatus.BAD_REQUEST,"10010045", "公司code为空"),
    ACCESSKEY_NAME_EMPTY(HttpStatus.BAD_REQUEST, "10010046", "AccesskeyDTO中的name参数为空"),
    ACCESSKEY_USERCODE_EMPTY(HttpStatus.BAD_REQUEST, "10010047", "AccesskeyDTO中的userCode参数为空"),
    USER_COMPANY_IS_NOT_EXISTED(HttpStatus.BAD_REQUEST,"10010048", "用户公司映射关系不存在"),


    //    api调用相关的异常提示
    SIGN_NO_RIGHT(HttpStatus.BAD_REQUEST, "10011000", "签名不合法"),
    COMPANY_IS_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "10011001", "用户公司信息失效"),
    API_PATH_NOT_EXIST(HttpStatus.BAD_REQUEST, "10011002", "api资源路径不存在"),
    KEY_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "10011003", "key令牌参数为空"),
    KEY_TOKEN_IS_NOT_RIGHT(HttpStatus.BAD_REQUEST,"10011004", "key 令牌不合法"),
    KEY_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST,"10011005", "key 令牌过期，请重新获取"),
    KEY_RESOURCECODE_QUOTA_IS_EXPIRED(HttpStatus.BAD_REQUEST,"10011006", "时间额度已过期，请重新购买"),
    KEY_RESOURCECODE_TIMES_QUOTA_IS_NULL(HttpStatus.BAD_REQUEST,"10011007", "调用次数额度已无，请重新购买"),
    TIMESTAMP_NO_RIGHT(HttpStatus.BAD_REQUEST, "10011008", "时间戳不合法"),
    KEY_IS_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "10011009", "key是无效的"),
    //    api调用相关的异常提示：

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


    public static HttpError getError(String code){
        for (HttpErrorEnum httpErrorEnum:HttpErrorEnum.values()){
            if (httpErrorEnum.getCode().equals(code)){
                return httpErrorEnum;
            }
        }
        return null;
    }
}
