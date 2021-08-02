package edu.poly.shop.controller.admin;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.Account;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.utils.ParamService;
import edu.poly.shop.utils.SessionService;

@Controller
@RequestMapping("admin")
public class LoginController {

	
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService session;
	@Autowired
	AccountService accountService;

	@GetMapping("login")
	public String viewForm() {
		
		return "admin/login";
	}

	@PostMapping("login")
	public String checkLogin(Model model) {
		String user = paramService.getString("username", "");
		String password = paramService.getString("password", "");

//		
//		
//		
//		try {
			Optional<Account> account = accountService.findById(user);
//			if (!account.get().getPassword().equalsIgnoreCase(password)) {
//				model.addAttribute("message", "sai ten tai khoan hoac mat khau");
//				
//			}else {
//				String uri = session.get("security-uri");
//				if (uri!=null) {
//					return "redirect:"+uri;
//				}else {
//					model.addAttribute("message", "dang nhap thanh cong");
//					session.set("user", account);
//					
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			model.addAttribute("message", "ten tai khoan khong ton tai");
//		}
//		return "admin/login";
		if (account.isEmpty()) {
			model.addAttribute("message", "Không tìm thấy thông tin tài khoản");
			return "admin/login";
		}
		else {
			if (account.get().getPassword().equals(password) && account.get().getRole()==1) {
				
				model.addAttribute("message", "Đăng nhập thành công");
				session.set("account", account.get());
				session.set("username", account.get().getUsername());
				session.get("account");
				model.addAttribute("account", account.get());
				
				return "redirect:/admin/home";
			}else {
				String uri = session.get("security-uri");
				if (uri!=null) {
					return "redirect:"+uri;
				}else {
					model.addAttribute("message", "sai tài khoản hoặc mật khẩu");
				}
				
				return "admin/login";
			}
		}
	

		// return"redirect:/admin/accountManage/viewAccount";
	}

	
//}
	@GetMapping("logout")
	public String logout() {
		session.remove("account");
		return "admin/login";
	}
}

