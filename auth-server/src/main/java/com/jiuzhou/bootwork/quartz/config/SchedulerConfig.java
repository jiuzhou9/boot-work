package com.jiuzhou.bootwork.quartz.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {
    @Autowired
    private SpringJobFactory springJobFactory;

    //注入数据库
    @Autowired
    private DataSource dataSource;


    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        /*SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());*/
        //        ReflectionUtils.accessibleConstructor(String.class, String.class);

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //注入数据库
        factory.setDataSource(dataSource);
        factory.setAutoStartup(true);
        factory.setStartupDelay(5);//延时5秒启动
        factory.setQuartzProperties(quartzProperties());
        //注意这里是重点
        factory.setJobFactory(springJobFactory);

        return factory;
    }

    /*@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }*/

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 设置quartz属性
     */
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");

        //		prop.put("org.quartz.dataSource.quartzDataSource.driver", druidProperties.getDriverClassName());
        //		prop.put("org.quartz.dataSource.quartzDataSource.URL", druidProperties.getUrl());
        //		prop.put("org.quartz.dataSource.quartzDataSource.user", druidProperties.getUsername());
        //		prop.put("org.quartz.dataSource.quartzDataSource.password", druidProperties.getPassword());
        //		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", druidProperties.getMaxActive());
        return prop;
    }

}
