package edu.poly.shop.controller.site;

import edu.poly.shop.domain.Product;
import edu.poly.shop.global.GlobalData;
import edu.poly.shop.model.CartItemDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.ShoppingCartService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("techshop")
public class HomeController {
	
	
	@Autowired
	ShoppingCartService shoppingCartService;
    @Autowired
    ProductService productService;
    @Autowired
    HttpSession session;
    @Autowired
    CategoryService categoryService;

    @GetMapping("about")
    public String aboutus(Model model) {
    	model.addAttribute("email", session.getAttribute("email"));
    	return "site/new/about";
    }
    @GetMapping("contact")
    public String contract(Model model) {
    	model.addAttribute("email", session.getAttribute("email"));
    	return "site/new/contact-us";
    }
    @GetMapping("myaccount")
    public String account(Model model) {
    	model.addAttribute("email", session.getAttribute("email"));
    	return "site/new/my-account";
    }
    
    @GetMapping("home")
    public String index(Model model,  @RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size){
    	List<Product> list= productService.findAll();
        model.addAttribute("products", list);
////      
        model.addAttribute("NoOfItem", shoppingCartService.getCount());
        Collection<CartItemDto> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmout());
		
		///tim kiem theo ten va phan trang 
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);
		
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("name"));
		Page<Product>resultPage = null;
		
		if (StringUtils.hasText(name)) {
			resultPage = productService.findByNameContaining(name, pageable);
			model.addAttribute("name",name);
		}else {
			resultPage = productService.findAll(pageable);
		}
		
		
		
		int totalPages = resultPage.getTotalPages();
		if (totalPages >0) {
			int start = Math.max(1, currentPage-2);
			int end = Math.min(currentPage+2, totalPages);	
			
			if (totalPages >5) {
				if (end== totalPages) start = end-5;
				else if(start ==1) end =start+5;
					
				
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			
			
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("name", name);
		model.addAttribute("productPage", resultPage);
		
		
		
		///tim kiem theo ten va phan trang 
		
		model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
        return "site/new/shop";
        
        

    }
    @ModelAttribute("getStatus")
	public Map<Integer, String> getRole() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "OutStock");
		map.put(2, "Sale");
		map.put(3, "Special");
		map.put(4, "Buy the most");
		return map;
	}
    @GetMapping("detail/{productId}")
    public String detail(Model model,@PathVariable("productId") Long productId) {
    	Optional<Product> opt = productService.findById(productId);
    	ProductDto dto = new ProductDto();
    	model.addAttribute("NoOfItem", shoppingCartService.getCount());
        Collection<CartItemDto> cartItems = shoppingCartService.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", shoppingCartService.getAmout());
    	if (opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			  model.addAttribute("product", dto);
			 
		}
    	model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
		return "site/new/shop-detail";
    	
    }
    // sort by unitprince
    @GetMapping("home/sortByUnitPrince")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size
			
			) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by(Direction.DESC, "unitPrince"));
		Page<Product>resultPage = null;
		
		if (StringUtils.hasText(name)) {
			resultPage = productService.findByNameContaining(name, pageable);
			model.addAttribute("name",name);
		}else {
			resultPage = productService.findAll(pageable);
		}
		
		
		
		int totalPages = resultPage.getTotalPages();
		if (totalPages >0) {
			int start = Math.max(1, currentPage-2);
			int end = Math.min(currentPage+2, totalPages);	
			
			if (totalPages >5) {
				if (end== totalPages) start = end-5;
				else if(start ==1) end =start+5;
					
				
			}
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			
			
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("name", name);
		model.addAttribute("productPage", resultPage);
		model.addAttribute("email", session.getAttribute("email"));
		System.out.println("email: "+ session.getAttribute("email") );
		return "site/new/shop";
		
		
	}

}
