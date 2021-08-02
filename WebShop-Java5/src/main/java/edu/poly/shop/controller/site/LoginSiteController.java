package edu.poly.shop.controller.site;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.model.CustomerLoginDto;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.utils.EmailUtils;

@Controller
public class LoginSiteController {

	@Autowired
	CustomerService customerService;
	@Autowired
	private HttpSession session;
	
	
	
	@GetMapping("techshop/login")
	public String viewLogin(Model model ) {
		model.addAttribute("customer", new CustomerLoginDto() );
		return "site/login";
	}
	@GetMapping("techshop/login/verify")
	public String verifyUser(@Param("code") String code) {
		if (customerService.verify(code)) {

			return "site/customers/verifi_success";
		} else {
			return "site/customers/verify_fail";
		}
	}
	
	@PostMapping("techshop/login")
	public ModelAndView login(ModelMap model, @ModelAttribute("customer")
		CustomerLoginDto dto
			) {
		Customer customer = customerService.login(dto.getEmail(), dto.getPassword());
		if (customer==null) {
			model.addAttribute("message", "Sai tài khoản hoặc mật khẩu");
			return new ModelAndView("site/login", model);
			
		} else if (customer.isEnabled()==false){
			model.addAttribute("message", "Tài khoản chưa được kích hoạt vui lòng kích hoạt để đăng nhập");
			return new ModelAndView("site/login", model);
		}
		session.setAttribute("email", customer.getEmail());
//		Object ruri = session.getAttribute("redirect-uri-site");
//		if (ruri!=null) {
//			session.removeAttribute("redirect-uri-site");
//			return new ModelAndView("riderect:"+ruri);
//		}
		
		
		return new ModelAndView("redirect:/techshop/home", model);
	}
	@GetMapping("techshop/logout")
	public String viewLogout(Model model ) {
		session.removeAttribute("email");
		return "redirect:/techshop/home";
	}

}
