package com.bigbang.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
    	aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	List<Parameter> aParameters = new ArrayList<>();
    	aParameters.add(aParameterBuilder.build());
    	
    	
        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(aParameters)
        		.select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                         
          .build();                                           
    }
    
    // This method not used
    private ApiInfo apiInfo() {
        return new ApiInfo("My REST API", "Some custom description of API.", "API TOS", "Terms of service", new Contact("John Doe", "www.example.com", "myeaddress@company.com"), "License of API", "API license URL", Collections.emptyList());
    }
}

