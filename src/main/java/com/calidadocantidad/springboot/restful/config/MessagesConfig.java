package com.calidadocantidad.springboot.restful.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author jalv
 * Configuración para que los mensajes de las validaciones se cojan del messages.properties
 */
@Configuration
public class MessagesConfig {
	
	/**
	 * Obtenemos la codificación del fichero del application.properties que a su vez la obtiene del fichero pom.xml
	 */
	@Value("${spring.messages.encoding}")
	private String encoding;
	
	/**
	 * Método con el que indicamos la ruta del fichero de mensajes y la codificación
	 * @return MessageSource con la ruta del fichero de mensajes y la codificación
	 */
	@Bean
	public MessageSource messageSource() {		
	    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding(this.encoding);  
	    return messageSource;
	}
	
	/**
	 * Método con el que indicamos que las validaciones cojan los mensajes del bean messageSource() 
	 * @return LocalValidatorFactoryBean que coge las validaciones cojan los mensajes del bean messageSource()
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
}
