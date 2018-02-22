<h1>boot-work</h1>
<h2>pom文件说明</h2>
<h3>依赖关系</h3>
这个依赖关系是spring-cloud官网给出的。

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.M7</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <!--依赖管理，用于管理spring-cloud的依赖，其中Finchley.M5是版本号-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Finchley.M5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
<h3>jar中没有主清单属性 问题</h3>
见下面
<h3>spring-boot-maven-plugin 问题</h3>
有时候我们的项目中打包完成，使用命令行启动的时候，会出现<strong>'jar包中主清单属性不存在'</strong>的现象，通常我们的处理方式会在项目中添加一段如下依赖，如下：

    <build>
        <!--jar 中没有主清单？-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

上面这段代码确实是解决问题的一个步骤，可能到此有些项目就OK了，但是有些不是OK的，在我们执行mvn clean package 打包的命令时会出现新的问题，如下：
    
    wangjiuzhoudeMacBook-Pro:eureka-server wangjiuzhou$ mvn clean package
    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------------------------------------------------------------
    [INFO] Building eureka-server 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [WARNING] The POM for org.springframework.boot:spring-boot-maven-plugin:jar:2.0.0.M7 is missing, no dependency information available
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD FAILURE
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 0.467 s
    [INFO] Finished at: 2018-02-19T18:20:28+08:00
    [INFO] Final Memory: 11M/155M
    [INFO] ------------------------------------------------------------------------
    [ERROR] Plugin org.springframework.boot:spring-boot-maven-plugin:2.0.0.M7 or one of its dependencies could not be resolved: 
    Failure to find org.springframework.boot:spring-boot-maven-plugin:jar:2.0.0.M7 in https://repo.maven.apache.org/maven2 was 
    cached in the local repository, resolution will not be reattempted until the update interval of central has elapsed or 
    updates are forced -> [Help 1]
    [ERROR] 
    [ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
    [ERROR] Re-run Maven using the -X switch to enable full debug logging.
    [ERROR] 
    [ERROR] For more information about the errors and possible solutions, please read the following articles:
    [ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/PluginResolutionException
    wangjiuzhoudeMacBook-Pro:eureka-server wangjiuzhou$ 

  对于这个问题，我想我们不能说是spring-cloud的bug吧，但是这个问题确实有些让人匪夷所思。因为你可能会去尝试在dependency节点中添加版本明确的
spring-boot-maven-plugin资源依赖，但是发现还是解决不了，总而言之mvn中央仓库中确实有这个资源的。那么下面说一下我的解决方式，因为这个资源
严格来说是属于spring提供的，所以我们需要将spring的仓库、插件来源这些依赖上。如下：

    <!--为了解决子模块在添加spring-boot-maven-plugin后依然报错的问题-->
    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <url>http://repo.spring.io/snapshot</url>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <url>http://repo.spring.io/milestone</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <url>http://repo.spring.io/snapshot</url>
        </pluginRepository>
        <pluginRepository>
            <id>spring-milestones</id>
            <url>http://repo.spring.io/milestone</url>
        </pluginRepository>
    </pluginRepositories>
    
  关于spring仓库、插件更多详细内容：https://repo.spring.io/webapp/#/home