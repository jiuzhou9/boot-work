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