/*
 * Copyright (c) 2017, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jiuzhou.bootwork.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * @ClassName : FastJsonHttpMessageConvertersConfiguration
 * @Description : 使用FastJSON作为json转换
 * @author: wangc
 * @version: v0.1
 * @Date: 2017-03-13 11:21 PM
 */
@Configuration
@ConditionalOnClass({ JSON.class })
public class FastJsonHttpMessageConvertersConfig {
    /**
     * JSON转换器
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                                             SerializerFeature.WriteNullNumberAsZero,
                                             SerializerFeature.WriteNullStringAsEmpty,
                                             SerializerFeature.WriteNullListAsEmpty,
                                             SerializerFeature.WriteNullBooleanAsFalse);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
