package com.api.backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.api.backend.modules"))
                .paths(PathSelectors.any())
                .build()
                // TODO: 기본으로 세팅되는 200, 401, 403, 404 메시지를 표시하지 않음.
                .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo(){
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("프론트엔드 개발시 사용되는 서버 API에 대한 연동 문서입니다.")
                .license("minsuKim").licenseUrl("http://localhost:8080").version("1").build();
    }
}
