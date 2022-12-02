package de.throsenheim.unlimited.stockeasilyapi.config;

import org.springframework.beans.factory.annotation.Value;
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
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.throsenheim.unlimited"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(getApiMetadata())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo getApiMetadata() {
        return new ApiInfoBuilder()
                .title("StockEasily")
                .description("StockEasily allows to search and manage your items with ease.")
                .version("0.0.1-SNAPSHOT")
                .build();
    }

}
