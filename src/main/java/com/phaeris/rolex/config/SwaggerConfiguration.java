package com.phaeris.rolex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author wyh
 * @since 2021/3/9 19:14
 */
@Configuration
@SuppressWarnings("SpellCheckingInspection")
public class SwaggerConfiguration {

    private static final String GROUP_NAME = "job";

    private static final String BASE_PACKAGE = "com.phaeris.rolex.controller";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName(GROUP_NAME)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Job")
                .description("定时任务服务")
                .contact(new Contact("Wyh", "https://github.com/PhaerisWakfu", "243409312@qq.com"))
                .version("1.0")
                .build();
    }
}
