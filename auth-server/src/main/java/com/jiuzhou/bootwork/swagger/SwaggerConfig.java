package com.jiuzhou.bootwork.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2017/12/26
 *
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                        .paths(Predicates.or(
                                        //这里添加你需要展示的接口
                                        PathSelectors.ant("/api/**")
                               )
                        ).build();
//                        .paths(Predicates.not(PathSelectors.regex("/error"))).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("auth-server").description("auth-server API文档").version("1.0").build();
    }
}
