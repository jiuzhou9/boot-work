auth-service
除登录外，其余所有操作均需要token认证。

不作为服务的模块不能有一下代码打包
    
    
    <build>
        <finalName>auth-service</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>application*.yml</exclude>
                        <exclude>logback.xml</exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
        </plugins>
    </build>
    
    网关业务层，目前已经实现如下功能：
    1.用户注册
    2.用户登录
    3.申请APP
    4.获取APP令牌
    5.APP令牌认证
    6.访问权限投票
    7.按次数计费
    下面是后面待完成的：
    8.按时间计费