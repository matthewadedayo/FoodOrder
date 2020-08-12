package com.Food.FoodOrder;

import com.Food.FoodOrder.utility.AppData;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	@Autowired
       private AppData appData;

		 @Bean
		    public Docket api() {
		        return new Docket(DocumentationType.SWAGGER_2).select()
		            .apis(RequestHandlerSelectors.basePackage("com.FoodOrder.controller"))
		            .paths(PathSelectors.any())
		            .build()
		            .apiInfo(apiInfo());
		    }

		 private ApiInfo apiInfo() {
		        return new ApiInfo(appData.APP_NAME, appData.APP_DESCRIPTION, appData.APP_VERSION, "Terms of service", new Contact(appData.APP_AUTHOR, appData.APP_URL, appData.APP_EMAIL), "API License", appData.APP_LICENSE_URL,
		            Collections.emptyList());
		    }
 }
