auth-server
认证服务，对于认证服务，所有对外暴露的接口中大概分为两类：
第一类：直接跟用户打交道的服务
    
    /api/v1/user/register
    /api/v1/user/create-user-token
    /api/v1/app/create
    /api/v1/app/refresh
    
第二类：网关打交道的服务，不能抛HTTPStatus异常，否则网关无法友好的处理异常

    /api/v1/app/check