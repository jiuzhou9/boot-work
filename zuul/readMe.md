<h1>zuul</h1>
网关，对外提供服务的模块，外界进行调用api的时候，须通过网关进行校验身份（后续添加）->权限的校验（后续添加）->请求映射。
可验证的链接：
http://localhost:15040/eureka-api/api/v1/hello
http://localhost:15040/eureka-api/api/v1/product/info/1
http://网关地址:网关端口/注册中心服务name/服务接口映射。


