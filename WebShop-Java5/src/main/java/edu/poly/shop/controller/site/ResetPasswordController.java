package edu.poly.shop.controller.site;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.utils.EmailUtils;

@Controller
@RequestMapping("techshop/customers")
public class ResetPasswordController {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	EmailUtils emailutils;
	@GetMapping("resetpassword")
	public String showform(Model model ) {
		return "site/forgotpassword";
	}
	
	@PostMapping("resetpassword")
	public String resetpassword(Model model,@RequestParam(name="email")String email ) {
		 
		Optional<Customer> opt =customerService.findByEmail(email);
		if (opt.isPresent()) {
			Customer customer =opt.get();
			customer.setPassword("abc123");
			customerService.save(customer);
			model.addAttribute("message", "Mật khẩu đã được gửi tới Email");
			emailutils.sendEmail(opt.get().getEmail(), "Đặt lại mật khẩu thành công", 
					"Xin chào "+opt.get().getName() + " ,\r\n"
							+ " \r\n"
							+ "Tài khoản của bạn đã được đặt lại thành công là 'abc123'."
							+ " Vui lòng đăng nhập TECHSHOP để tiếp tục mua hàng .\r\n"
					);
			
			return "site/forgotpassword";
		}
		model.addAttribute("message", "Không tìm thấy Email");
		return "site/forgotpassword";
	}
}
