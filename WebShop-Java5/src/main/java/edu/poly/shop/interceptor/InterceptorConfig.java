package edu.poly.shop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	AuthInterceptor auth;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auth)
			.addPathPatterns(
					
					"/admin/**"
					)// khong cho phep truy cap vao
			.excludePathPatterns("/static/**","/admin/home","/admin/login");//cho phep truy cap 
	}
	
	
}
