package com.marcolenzo.gameboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

/**
 * Converters configuration.
 * @author Marco Lenzo
 *
 */
@Configuration
public class ConvertersConfig {

	@Bean
	public ConversionServiceFactoryBean conversionService() {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		return factory;
	}

}
