package edu.poly.shop.controller.site;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.service.CustomerService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("techshop/customers")
public class CustomerSiteController {
	
	@Autowired
	CustomerService customerService;

	
	@GetMapping("signup")
	public String add(Model model) {
		model.addAttribute("customer", new CustomerDto());
		
		return "site/signup";
	}
//	@GetMapping("saveOrUpdate")
//	public ModelAndView checkemail(Model model,@ModelAttribute("customer") CustomerDto dto) {
//		Optional<Customer> opt = customerService.findByEmail(dto.getEmail());
//		if (opt.isPresent()) {
//			System.out.println("present");
//			return new ModelAndView("redirect:/techshop/login");
//			
//		}
//		return null;
//	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,@ModelAttribute("customer") CustomerDto dto, HttpServletRequest request
//			@RequestParam(name="txtEmail", required = true) String email
			)   throws UnsupportedEncodingException, MessagingException
	{
		
		
		Optional<Customer> opt = customerService.findByEmail(dto.getEmail());
		if (opt.isPresent()) {
			System.out.println("present");
			model.addAttribute("message", "Email đã tồn tại");
			return new ModelAndView("site/signup",model);
			
		}else {
			Customer entity = new Customer();
			BeanUtils.copyProperties(dto, entity);
			entity.setStatus((short) 1);
			customerService.register(entity,getSiteURL(request));
			model.addAttribute("message", "Đăng ký thành công");
			model.addAttribute("customer", new CustomerDto());
			return new ModelAndView("site/signup");
		}
	
	}
	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
