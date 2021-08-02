package edu.poly.shop.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;

import edu.poly.shop.model.OrderDto;

import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;

@Controller
@RequestMapping("admin/orders")
public class OrderController {

	@Autowired
	CustomerService customerService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderdetailService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("order", new OrderDto());
		
		return "admin/orders/addOrEdit";
	}
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("order") OrderDto dto,
			@RequestParam("customerId") Long customerId,
			BindingResult result) throws ParseException {

//		if (result.hasErrors()) {
//			return new ModelAndView("admin/categories/addOrEdit");
//		}
		
		
		Order entity = new Order();
		
		BeanUtils.copyProperties(dto, entity);
		Customer customer = new Customer();
		customer.setCustomerId(dto.getCustomerId());
		entity.setCustomer(customer);
		
	
		System.out.print("customerId:"+dto.getCustomerId());
//		entity.setCustomer(1);
		orderService.save(entity);

		model.addAttribute("message", "Lưu đơn đặt hàng thành công");
		return new ModelAndView("admin/orders/addOrEdit", model); // return new
																		// ModelAndView("forwar:/admin/categories/addOrEdit",model);
		// de chuyen den trang add, khong chuyen den trnag list
	}
	
	@GetMapping("edit/{orderId}")
	public ModelAndView edit(ModelMap model, @PathVariable("orderId") Long orderId) {
		Optional<Order> opt = orderService.findById(orderId);
		OrderDto dto = new OrderDto();

		if (opt.isPresent()) {
			Order entity = opt.get();
//			Customer customer = new Customer();
			
			BeanUtils.copyProperties(entity, dto);
			dto.setCustomerId(entity.getCustomer().getCustomerId());
			dto.setIsEdit(true);

			model.addAttribute("order", dto);
//			model.addAttribute("message", "Cập nhật danh mục thành công");

			return new ModelAndView("admin/orders/addOrEdit", model);
		}

		model.addAttribute("message", "Không tìm thấy đơn hàng");

		return new ModelAndView("forward:/admin/orders/searchpaginated");
	}
	@GetMapping("delete/{orderId}")
	public ModelAndView delete(ModelMap model, @PathVariable("orderId") Long orderId) {

		if (orderdetailService.findById(orderId)==null) {
			orderService.deleteById(orderId);
			model.addAttribute("message", "Xóa đơn hàng thành công");
			return new ModelAndView("forward:/admin/orders/searchpaginated");
		}else {
			model.addAttribute("error", "Xóa đơn hàng không thành công");
			return new ModelAndView("forward:/admin/orders/searchpaginated");
		}
		

			
		

	}
	
	
	@GetMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "orderId", required = false) Long orderId,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size

	) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("orderId"));
		Page<Order> resultPage = null;
		
		
		if (orderId == null) {
			resultPage = orderService.findAll(pageable);
			model.addAttribute("orderId", orderId);
		
		} else {
			
			resultPage = orderService.findByOrderIdContaining(orderId, pageable);
			
			
		}

		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);

			if (totalPages > 5) {
				if (end == totalPages)
					start = end - 5;
				else if (start == 1)
					end = start + 5;

			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("orderId", orderId);
		model.addAttribute("orderPage", resultPage);

		return "admin/orders/searchpaginated";

	}
}
