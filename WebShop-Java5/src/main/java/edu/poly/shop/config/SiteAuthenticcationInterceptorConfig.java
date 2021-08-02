//package edu.poly.shop.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import edu.poly.shop.interceptor.AuthenticationSiteInterceptor;
//
//@Configuration
//public class SiteAuthenticcationInterceptorConfig implements WebMvcConfigurer {
//
//	@Autowired
//	AuthenticationSiteInterceptor authsite;
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(authsite)
//		
//		.excludePathPatterns("/techshop/home","/techshop/login");//cho phep truy cap 
//	}
//
//	
//}
//
