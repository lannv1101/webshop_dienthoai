package edu.poly.shop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.utils.SessionService;

@Controller
@RequestMapping("admin")
public class HomeAdminController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderdetailService;
	@Autowired
	CustomerService customerService;
	@Autowired
	SessionService session;
	@RequestMapping("home")
	public String index(Model model) {
	
		System.out.println("checklogin:"+session.get("account"));
		
			model.addAttribute("message", session.get("username"));
			model.addAttribute("account", session.get("account"));
		model.addAttribute("countorders", orderService.countCustomerId());
		model.addAttribute("SumAmountOrders", orderService.sumAmountOrders());
		model.addAttribute("CountCustomers", customerService.countCustomer());
		model.addAttribute("CountQuantity", orderdetailService.countByQuantity());
		return "admin/index";
	}

}
