<h1>feign-consumer</h1>
巨坑，这个模块里有一个坑，弄了一天才弄出来，具体情况如下：
    
    2018-03-20 14:42:27.203  INFO 4063 --- [           main] s.d.spring.web.caching.CachingAspect     : Caching aspect applied for cache modelProperties with key com.jiuzhou.bootwork.result.Result(true)
    2018-03-20 14:42:27.207  WARN 4063 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'productServiceImpl': Unsatisfied dependency expressed through field 'productApiClient'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.jiuzhou.bootwork.service.ProductApiClient': FactoryBean threw exception on object creation; nested exception is java.lang.NullPointerException
    2018-03-20 14:42:27.209  INFO 4063 --- [           main] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
    2018-03-20 14:42:27.221  INFO 4063 --- [           main] ConditionEvaluationReportLoggingListener : 
    
    Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
    Disconnected from the target VM, address: '127.0.0.1:64107', transport: 'socket'
    2018-03-20 14:42:27.230 ERROR 4063 --- [           main] o.s.boot.SpringApplication               : Application run failed
    
    org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'productServiceImpl': Unsatisfied dependency expressed through field 'productApiClient'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.jiuzhou.bootwork.service.ProductApiClient': FactoryBean threw exception on object creation; nested exception is java.lang.NullPointerException
    	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:587) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:91) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:373) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1344) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:502) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:312) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:228) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:310) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:760) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:868) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:549) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) ~[spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) [spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:388) [spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.boot.SpringApplication.run(SpringApplication.java:327) [spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1246) [spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1234) [spring-boot-2.0.0.BUILD-20180301.043920-633.jar:2.0.0.BUILD-SNAPSHOT]
    	at com.jiuzhou.bootwork.FeignConsumer.main(FeignConsumer.java:21) [classes/:na]
    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.jiuzhou.bootwork.service.ProductApiClient': FactoryBean threw exception on object creation; nested exception is java.lang.NullPointerException
    	at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.doGetObjectFromFactoryBean(FactoryBeanRegistrySupport.java:168) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.getObjectFromFactoryBean(FactoryBeanRegistrySupport.java:101) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.getObjectForBeanInstance(AbstractBeanFactory.java:1645) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.getObjectForBeanInstance(AbstractAutowireCapableBeanFactory.java:1178) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:258) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:200) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:251) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultListableBeanFactory.addCandidateEntry(DefaultListableBeanFactory.java:1325) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultListableBeanFactory.findAutowireCandidates(DefaultListableBeanFactory.java:1291) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1101) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1065) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:584) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	... 19 common frames omitted
    Caused by: java.lang.NullPointerException: null
    	at springfox.documentation.schema.property.OptimizedModelPropertiesProvider.beanDescription(OptimizedModelPropertiesProvider.java:317) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.schema.property.OptimizedModelPropertiesProvider.propertiesFor(OptimizedModelPropertiesProvider.java:117) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.schema.property.OptimizedModelPropertiesProvider$$FastClassBySpringCGLIB$$cb306bb2.invoke(<generated>) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at springfox.documentation.spring.web.caching.CachingAspect.cachedValue(CachingAspect.java:86) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.caching.CachingAspect.operationsAndProperties(CachingAspect.java:69) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_152]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_152]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_152]
    	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_152]
    	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:643) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:632) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:174) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at springfox.documentation.schema.property.OptimizedModelPropertiesProvider$$EnhancerBySpringCGLIB$$2395299f.propertiesFor(<generated>) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.schema.DefaultModelProvider.properties(DefaultModelProvider.java:151) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.schema.DefaultModelProvider.modelFor(DefaultModelProvider.java:85) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.schema.DefaultModelProvider$$FastClassBySpringCGLIB$$2c8f6146.invoke(<generated>) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at springfox.documentation.spring.web.caching.CachingAspect.cachedValue(CachingAspect.java:86) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.caching.CachingAspect.modelsAndDependencies(CachingAspect.java:80) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_152]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_152]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_152]
    	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_152]
    	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:643) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:632) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:174) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689) ~[spring-aop-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at springfox.documentation.schema.DefaultModelProvider$$EnhancerBySpringCGLIB$$421e26db.modelFor(<generated>) ~[springfox-schema-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.scanners.ApiModelReader.read(ApiModelReader.java:66) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.scanners.ApiListingScanner.scan(ApiListingScanner.java:91) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.scanners.ApiDocumentationScanner.scan(ApiDocumentationScanner.java:62) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper.scanDocumentation(DocumentationPluginsBootstrapper.java:101) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper.onApplicationEvent(DocumentationPluginsBootstrapper.java:87) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper.onApplicationEvent(DocumentationPluginsBootstrapper.java:50) ~[springfox-spring-web-2.2.2.jar:2.2.2]
    	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:399) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:405) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:353) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.finishRefresh(AbstractApplicationContext.java:887) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:552) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	at org.springframework.cloud.context.named.NamedContextFactory.createContext(NamedContextFactory.java:117) ~[spring-cloud-context-2.0.0.BUILD-20180319.235337-2296.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.cloud.context.named.NamedContextFactory.getContext(NamedContextFactory.java:85) ~[spring-cloud-context-2.0.0.BUILD-20180319.235337-2296.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.cloud.context.named.NamedContextFactory.getInstance(NamedContextFactory.java:126) ~[spring-cloud-context-2.0.0.BUILD-20180319.235337-2296.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.cloud.openfeign.FeignClientFactoryBean.get(FeignClientFactoryBean.java:205) ~[spring-cloud-openfeign-core-2.0.0.BUILD-20180319.234534-219.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.cloud.openfeign.FeignClientFactoryBean.feign(FeignClientFactoryBean.java:84) ~[spring-cloud-openfeign-core-2.0.0.BUILD-20180319.234534-219.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.cloud.openfeign.FeignClientFactoryBean.getObject(FeignClientFactoryBean.java:233) ~[spring-cloud-openfeign-core-2.0.0.BUILD-20180319.234534-219.jar:2.0.0.BUILD-SNAPSHOT]
    	at org.springframework.beans.factory.support.FactoryBeanRegistrySupport.doGetObjectFromFactoryBean(FactoryBeanRegistrySupport.java:161) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
    	... 30 common frames omitted
    
    
    Process finished with exit code 1


这个异常中有"springfox"包结构，后来发现升级了一下swagger版本就好了，搞了老子一整天的坑，swagger原来版本是2.2.2升级后成为2.8.0问题就没了。