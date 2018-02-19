<h1>eureka-server</h1>
<h2>服务注册中心，下面是时间记录</h2>
<h3>v1.0</h3>

`2018-02-17：`

    1.当前服务注册中心属于单节点服务注册中心；
    2.当前的注册中心存在自我保护模式:eureka.server.enable-self-preservation=false可以解决
    (EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. 
    RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.)
    3.更改Eureka更新频率将打破服务器的自我保护功能，生产环境下不建议自定义这些配置。
    
`2018-02-18：`

    1.实现了注册中心的高可用性。采用一个project，两个server部署的策略，实现一个小集群。
        这么做的目的就是两个serve中维护的是同一套服务列表，如果eureka-server(peer1)挂掉，那么peer2还可以继续提供服务。
    2.启动步骤如下：
        命令：mvn clean package
        命令：   java -jar xxx.jar --spring.profiles.active=peer1
                java -jar xxx.jar --spring.profiles.active=peer2
    3.访问：http://localhost:15011/；http://localhost:15012/
            
