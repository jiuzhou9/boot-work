package com.jiuzhou.bootwork.excep;

import org.springframework.http.HttpStatus;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2018/03/28
 */
public enum HttpErrorEnum implements HttpError {

    USERNAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010001", "用户名已经存在"),
    MOBILE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010002", "手机号码已经存在"),
    USER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010003", "用户参数为空"),
    MOBILE_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010004", "手机参数为空"),
    USERNAME_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010005", "用户名参数为空"),
    PASSWORD_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010006", "密码参数为空"),
    USERNAME_PARAMETER_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010007", "用户名参数查询到多条数据结果"),
    MOBILE_PARAMETER_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010008", "手机号参数查询到多条数据结果"),
    USER_ROLE_ID_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010009", "用户-角色ID参数为空或者为0"),
    USER_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010010", "用户ID不存在"),

    USER_ROLE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010011", "用户角色信息为空"),
    USER_ROLE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "10010012", "该用户角色映射已经存在"),
    SERVER_NAME_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010013", "服务名参数查询到多条数据结果"),
    ROLE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010014", "角色信息为空"),
    ROLE_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010015", "角色名称为空"),
    ROLE_NAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "10010016", "角色名称已经存在，请更换新名称"),
    ROLE_NAME_PARAMETER_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010017", "角色名称参数查询到多条数据结果"),
    SERVER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010018", "服务信息为空"),
    SERVER_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010019", "服务名为空"),
    SERVER_DESCRIPTION_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010020", "服务描述空"),

    SERVER_NAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010021", "服务名已经存在"),
    ROLE_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010022", "角色ID不存在"),
    RESOURCE_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010023", "资源ID不存在"),
    ROLE_ID_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010024", "角色ID参数为空或者为0"),
    RESOURCE_ID_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010025", "资源ID参数为空或者为0"),
    USER_ID_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010026", "用户ID参数为空或者为0"),
    SERVER_ID_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010027", "服务ID参数为空或者为0"),
    RESOURCE_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010028", "资源名称参数为空"),
    RESOURCE_URL_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010029", "资源url参数为空"),
    RESOURCE_TYPE_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010030", "资源请求方式参数为空"),

    RESOURCE_TYPE_PARAMETER_IS_ERROR(HttpStatus.BAD_REQUEST, "10010031", "资源请求方式参数类型错误"),
    SERVER_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010032", "服务ID不存在"),
    SERVER_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "10010033", "该服务已经存在"),
    RESOURCE_URL_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010034", "资源URL参数查询到多条数据结果"),
    ROLE_RESOURCE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST, "10010035", "该角色资源映射已经存在"),
    ROLE_NAME_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010036", "该角色名字不存在"),
    ROLE_RESOURCE_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010037", "角色资源参数为空"),
    ROLE_RESOURCE_ID_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010038", "角色资源id参数为空"),
    ROLE_RESOURCE_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010039", "角色资源ID不存在"),

    ROLE_ID_RESOURCE_ID_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010040", "角色ID、资源ID参数查询到多条数据结果"),
    USER_ROLE_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010041", "用户角色ID不存在"),
    USER_ID_ROLE_ID_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010042", "用户ID、角色ID参数查询到多条数据结果"),
    APP_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010043", "APP参数为空"),
    APP_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010044", "APP名字参数为空"),
    USER_ID_APP_NAME_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010045", "该用户下APP名字已经存在，请重新定义"),
    USER_ID_APP_NAME_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST,"10010046", "该用户id、APP名字参数查询到多条数据"),
    APP_ID_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010047", "APP id参数为空"),
    APP_ID_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010048", "APP id参数不存在"),
    APP_ID_ROLE_ID_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST,"10010049", "APP ID、角色ID参数查询到多条数据结果"),

    APP_ROLE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010050", "app角色参数为空"),
    APP_ROLE_HAS_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,"10010051", "该APP角色已经存在，请重新定义"),
    PASSWORD_IS_NOT_RIGHT(HttpStatus.BAD_REQUEST,"10010052", "密码错误，请重新登录认证"),
    USERNAME_NOT_EXITED(HttpStatus.BAD_REQUEST,"10010053", "用户名不存在"),
    USER_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST,"10010054", "用户令牌过期，请重新获取"),
    USER_TOKEN_IS_NOT_RIGHT(HttpStatus.BAD_REQUEST,"10010055", "用户令牌不正确"),
    APP_NAME_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "10010056", "APP名字不存在"),
    APP_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "10010057", "APP更新失败"),
    USER_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST,"10010058", "用户令牌不能为空"),
    APP_TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010059", "APP令牌参数为空"),

    APP_CODE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010060", "APPcode参数为空"),
    APP_CODE_QUERY_MANY_RESULTS(HttpStatus.BAD_REQUEST, "10010061", "APPcode参数参数查询到多条数据"),
    APP_TOKEN_IS_NOT_RIGHT(HttpStatus.BAD_REQUEST,"10010062", "app令牌不正确"),
    APP_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST,"10010063", "app令牌过期，请重新获取"),
    APP_TOKEN_CHECK_FAILED(HttpStatus.BAD_REQUEST,"10010064", "app令牌身份认证失败"),
    APP_CODE_IS_NOT_EXIT(HttpStatus.BAD_REQUEST, "10010065", "APPcode不存在"),
    USER_IS_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "10010066", "用户为无效用户"),
    METHOD_TYPE_IS_EMPTY(HttpStatus.BAD_REQUEST, "10010067", "请求方式(RequestMethod:GET/POST/PUT等)为空"),
    HAS_NO_AUTHORITY(HttpStatus.BAD_REQUEST, "10010068", "无权限"),
    TIME_AUTOGRAPH_NO_RIGHT(HttpStatus.BAD_REQUEST, "10010069", "时间戳、签名不合法"),
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
