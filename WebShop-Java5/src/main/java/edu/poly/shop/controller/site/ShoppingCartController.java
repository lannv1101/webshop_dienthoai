package edu.poly.shop.controller.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.CartItem;
import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CartItemDto;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.model.OrderDetailDto;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.ShoppingCartService;
import edu.poly.shop.utils.EmailUtils;
import edu.poly.shop.utils.SessionService;

@Controller
@RequestMapping("techshop")
public class ShoppingCartController {

	@Autowired
	ProductService productService;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderdetailService;
	@Autowired
	CustomerService customerService;
	@Autowired
	HttpSession session;
	@Autowired
	EmailUtils mailutils;

	@GetMapping("cart")
	public String list(Model model) {
		Collection<CartItemDto> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmout());
		model.addAttribute("NoOfItem", shoppingCartService.getCount());
		model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
		return "site/new/cart";
	}

	@GetMapping("addToCart/{productId}")
	public String add(Model model,@PathVariable("productId") Long productId) {
		Optional<Product> opt = productService.findById(productId);
		CartItemDto cartDto = new CartItemDto();
		if (opt.isPresent()) {
			Product item =opt.get();
			item.setQuantity(1); // ngam dinh add 1 sp trong gio hang
			BeanUtils.copyProperties(item,cartDto);
		
			shoppingCartService.add(cartDto);
			
		}
		model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
		return "redirect:/techshop/home";
	}

	@GetMapping("cart/removeItem/{productId}")
	public String remove(Model model,@PathVariable("productId") Long productId) {
		shoppingCartService.remove(productId);
//		session.removeAttribute(shoppingCartService.remove(productId));
		model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
		return "redirect:/techshop/cart";
	}

	@PostMapping("update")
	public String update(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity ) {
		shoppingCartService.update(productId, quantity);
		
		return "redirect:/techshop/cart";
	}

	@GetMapping("clear")
	public String clear() {
		shoppingCartService.clear();
		return "site/cartItem/cartItem";

	}
//	@GetMapping("cart/checkout")
//	public String checkout(Model model) {
//		
//		model.addAttribute("total", shoppingCartService.getAmout());
//		model.addAttribute("NoOfItem", shoppingCartService.getCount());
//		Collection<CartItemDto> cartItems = shoppingCartService.getCartItems();
//		session.set("CartItem", cartItems);
//		model.addAttribute("cartItems", cartItems);
//		System.out.print("cartItem :" + cartItems);
//		model.addAttribute("customer", new CustomerDto());
//		return "site/cartItem/checkout";
//	}
	@PostMapping("cart/checkout")
	public String pay(Model model, @ModelAttribute("customer") CustomerDto customerDto) {
		

		session.getAttribute("email");
		if (session.getAttribute("email")==null) {
			
			return "redirect:/techshop/login";
		}
	
		
		Optional<Customer> customer = customerService.findByEmail((String) session.getAttribute("email"));
		System.out.println("session: "+session.getAttribute("email"));
		

		
		Order order = new Order();
		order.setAmount(shoppingCartService.getAmout());
		order.setOrderDate(new Date());
		order.setStatus((short) 1);
		
		
		order.setCustomer(customer.get());
		orderService.save(order);
		
		Collection<CartItemDto> cartItems = shoppingCartService.getCartItems();
		
		
		List<OrderDetail> details = new ArrayList<>();
		
		for (CartItemDto cartitemdto : cartItems) {
			OrderDetail detail = new OrderDetail();
			detail.setOrder(order);
			Product product = new Product();
			product.setProductId(cartitemdto.getProductId());
			detail.setProduct(product);
			detail.setUnitPrince(cartitemdto.getUnitPrince());
			detail.setQuantity(cartitemdto.getQuantity());
			details.add(detail);
		
			
			Product pr = productService.getById(cartitemdto.getProductId());
			pr.setQuantity(pr.getQuantity()- cartitemdto.getQuantity());
			productService.save(pr);
		}
		orderService.create(order, details);
		

	
		System.out.print("Thanh cong: "+order.getOrderId());
		mailutils.sendEmail(customer.get().getEmail(), "Đặt hàng Thành Công, mã đơn hàng #"+order.getOrderId(),
				
				"Xin chào "+customer.get().getName() + " ,\r\n"
				+ " \r\n"
				+ "Đơn hàng #"+order.getOrderId()+" của bạn đã được đặt thành công ngày "+order.getOrderDate()+ "\r\n"
				+ "\r\n"
				+ "Vui lòng đăng nhập TECHSHOP để xác bạn theo dõi và nhận hàng với sản phẩm trong vòng 3 ngày.\r\n"
				+ "Chúng tôi sẽ gọi điện thoại để xác nhận bạn đã đặt hàng và chuẩn bị giao hàng, bạn vui lòng giữ điện thoại trong ít phút");
		shoppingCartService.clear();
		model.addAttribute("email", session.getAttribute("email"));
		return "site/new/cart";
	}
}
