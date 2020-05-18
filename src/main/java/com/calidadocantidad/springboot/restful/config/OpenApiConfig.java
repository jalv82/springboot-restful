package com.calidadocantidad.springboot.restful.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author jalv
 * Configuración de OpenAPI
 */
@Configuration
@PropertySource("classpath:messages.properties")
public class OpenApiConfig {
	
	/**
	 * Número de versión
	 */
	@Value("${openApiConfig.version}")
	private String version;
	
	/** 
	 * Título del api
	 */
	@Value("${openApiConfig.title}")
	private String title;
	
	/**
	 * Descripción del api
	 */
	@Value("${openApiConfig.description}")
	private String description;
	
	
	/**
	 * Método con el que personalizamos los textos
	 * @return OpenApi con los textos personalizados
	 */
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                		.version(this.version)
                		.title(this.title)                		
                		.description(this.description));
    }
}
