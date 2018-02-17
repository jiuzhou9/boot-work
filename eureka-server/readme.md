**eureka-server**

`服务注册中心，下面是时间记录`

`2018-02-17：`

    1.当前服务注册中心属于单节点服务注册中心；
    2.当前的注册中心存在自我保护模式:eureka.server.enable-self-preservation=false可以解决
    (EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. 
    RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.)
    3.更改Eureka更新频率将打破服务器的自我保护功能，生产环境下不建议自定义这些配置。