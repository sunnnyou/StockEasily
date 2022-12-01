package de.throsenheim.unlimited.stockeasilyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class StockEasilyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockEasilyApiApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.throsenheim.unlimited"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(metadata());
    }

    private static ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("StockEasily")
                .description("StockEasily is a software system for organizing your home and stuff.\n" +
                        "It allows to search and manage your items with ease.")
                .version("1.0")
                .build();
    }

}
