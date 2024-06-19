package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.config.handler.AuthenticInterceptor;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
	
	@Value("${resources.location}")
	private String resourceLocation;
	
	@Value("${resources.uri_path}")
	private String resourceUriPath;
	
	@Autowired
    AuthenticInterceptor authenticInterceptor;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler(resourceUriPath + "/**")
    	.addResourceLocations("file:///" + resourceLocation);
    }
    
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
         registry.addInterceptor(authenticInterceptor);
    }
}
