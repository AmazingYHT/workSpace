//package com.cnaidun.user.api.policeUser.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * 项目名称：PoliceCloud
// * 类名称：SwaggerConfig
// * 类描述：
// * 创建人：JackJun
// * 创建时间：2018/7/25
// * 修改人：JackJun
// * 修改时间：2018/7/25
// * 修改备注：
// * 版权所有权：江苏艾盾网络科技有限公司
// *
// * @version V1.0
// */
//
////@Configuration
//@EnableSwagger2
//@EnableWebMvc
//public class SwaggerConfig {
//
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cnaidun.user.api.policeUser.model.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("重庆微警务-api接口")
//                .description("重庆微警务-api接口")
//                .version("1.0")
//                .build();
//    }
//
//}
