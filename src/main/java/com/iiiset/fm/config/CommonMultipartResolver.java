package com.iiiset.fm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonMultipartResolver {
	@Configuration 
	public class CoreConfig {

	    @Bean
	    public CommonsMultipartResolver commonsMultipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(50 * 1024 * 1024);
	    return multipartResolver;
	    }

	}

}
