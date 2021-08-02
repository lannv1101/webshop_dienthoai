package edu.poly.shop.controller.site;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;

@Configuration
@RequestMapping("techshop")
public class SiteCheckOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	HttpSession session;
	@Autowired
	CustomerService customerService;
	
	@GetMapping("checkorders")
	public String checkOrders(Model model) {
		model.addAttribute("email", session.getAttribute("email"));
		Optional<Customer> customer= customerService.findByEmail((String) session.getAttribute("email"));
		
		if (session.getAttribute("email")==null) {
			return "redirect:/techshop/login";
		}
		
		
		List<Order> checkorders=  orderService.findByCustomerId(customer.get().getCustomerId());
	
		
//		List<Order> order= orderService.findByCustomerIdContaining(customer.get().getCustomerId());
		model.addAttribute("orders", checkorders);
		return "site/new/orders";
		
	}
}
