//package edu.poly.shop.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//@Component
//public class AuthenticationSiteInterceptor implements HandlerInterceptor {
//
//	@Autowired
//	HttpSession session;
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//
//		System.out.println("pre handle of request"+ request.getRequestURI());
//		if (session.getAttribute("email") != null) {
//			return true;
//		}
//		session.setAttribute("redirect-uri-site", request.getRequestURI());
//		
//		response.sendRedirect("/techshop/login");
//		return false;
//	}
//
//}
