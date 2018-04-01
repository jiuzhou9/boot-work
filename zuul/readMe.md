<h1>zuul</h1>
网关，对外提供服务的模块，外界进行调用api的时候，须通过网关进行校验身份（后续添加）->权限的校验（后续添加）->请求映射。
可验证的链接：
http://localhost:15040/eureka-api/api/v1/hello
http://localhost:15040/eureka-api/api/v1/product/info/1
http://网关地址:网关端口/注册中心服务name/服务接口映射。

Caused by: java.lang.ClassNotFoundException: feign.Feign$Builder 解决方法

    <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
    
网关调用认证服务，一定不能抛异常，否则没办法捕获异常。