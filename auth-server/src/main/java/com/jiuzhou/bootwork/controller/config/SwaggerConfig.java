package com.jiuzhou.bootwork.controller.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/04/13
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //Header
        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder code = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();

        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        code.name("code").description("code").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(code.build());

        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .select()
                        .paths(
                                        Predicates.or(
                                                        PathSelectors.ant("/api/**")
                                        )
                        ).build()
                        .globalOperationParameters(pars)
                        .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("auth-server").description("auth-server API文档").version("1.0").build();
    }
}
