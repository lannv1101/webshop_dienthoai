package edu.poly.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import edu.poly.shop.domain.Account;
import edu.poly.shop.utils.SessionService;

@Service
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		Account user = sessionService.get("account");

		String error = "";
		if (user == null) {
			error = "please login!";
		} else if (!(user.getRole() == 1 && uri.startsWith("/admin/")) ) {
			error = "access denied";
		}
		if (error.length() > 0) {
			sessionService.set("security-uri", uri);
			response.sendRedirect("/admin/login?error=" + error);
			
			return false;
		}
		return true;
	}

}
