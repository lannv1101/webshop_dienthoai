package edu.poly.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import ch.qos.logback.core.net.SyslogOutputStream;
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

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.utils.FileuploadUtils;

@Controller
@RequestMapping("admin/products")
public class ProductController {
 public static  String uploadDir = System.getProperty("user.dir") +"/src/main/resources/static/upload";

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderDetailService oderdetailService;
	@Autowired
	ServletContext application;// luu anh

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("product", new ProductDto());
		List<Category> list = categoryService.findAll();

		model.addAttribute("categories", list);
		return "admin/products/addOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,@Valid @ModelAttribute("product") ProductDto productDto,
			 BindingResult result, @RequestParam("imgName") String imageName
			
		
			
			)  throws IOException {

		if (result.hasErrors()) {
			List<Category> list = categoryService.findAll();

			model.addAttribute("categories", list);
			return new ModelAndView("admin/products/addOrEdit");
		}

		model.addAttribute("categories", categoryService.findAll());
//
//		Product product = new Product();
////		
//		
////
//		String imageUUID;
//		if (!productDto.getImageFile().isEmpty()){
//			imageUUID = productDto.getImageFile().getOriginalFilename();
//			Path fileNameAndPath =Paths.get(uploadDir,imageUUID);
//			Files.write(fileNameAndPath, productDto.getImageFile().getBytes());
//
//		}else{
//			imageUUID= productDto.getName();
//		}
//		
//		productDto.setImage(imageUUID);
//		
//		BeanUtils.copyProperties("productDto", "product");
//		Category category = new Category();
//		category.setCategoryId(productDto.getCategoryId());
//		product.setCategory(category);
//		productService.save(product);

		String  baseDir = "D:\\Java5Eclipse\\WebShop-Java5\\src\\main\\resources\\static\\upload\\";
		
		String filename;
		if (!productDto.getImageFile().isEmpty()) {
			filename = productDto.getImageFile().getOriginalFilename();
			productDto.getImageFile().transferTo(new File(baseDir+filename));
		}else {
			filename= imageName;
		}
		
		
		Product product = new Product();
		
		productDto.setImage(filename);
		BeanUtils.copyProperties(productDto, product);
		Category category = new Category();
		category.setCategoryId(productDto.getCategoryId());
		product.setCategory(category);
		productService.save(product);


		model.addAttribute("message", "Lưu sản phẩm thành công");
		return new ModelAndView("admin/products/addOrEdit", model); // return new
																	// ModelAndView("forwar:/admin/categories/addOrEdit",model);
		// de chuyen den trang add, khong chuyen den trnag list
	}

	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		model.addAttribute("categories", categoryService.findAll());
		if (opt.isPresent()) {
			Product entity = opt.get();
			
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			
			model.addAttribute("product", dto);
//			model.addAttribute("message", "Cập nhật danh mục thành công");
			
			return new ModelAndView("admin/products/addOrEdit",model);
		}
		
		model.addAttribute("message", "Không tìm thấy danh mục");
		
		return new ModelAndView( "forward:/admin/categories");
	}
	
	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") Long productId) {
		
	
//		if (ordt.isEmpty()) {
			productService.deleteById(productId);
			model.addAttribute("message", "Xóa sản phẩm thành công");
			return new ModelAndView("forward:/admin/products/searchpaginated") ;
//		}else {
//			model.addAttribute("error", "Xóa sản phẩm không thành công");
//			
//			
//			
//			
//			return new ModelAndView("forward:/admin/products/searchpaginated") ;
//		}
		
	}
	@RequestMapping("")
	public String list(ModelMap model) {
		
		List<Product>list= productService.findAll();
		model.addAttribute("products", list);
		return "admin/products/list";
	}
	
	
	@GetMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size
			
			) {
		
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
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

		model.addAttribute("name", name);
		model.addAttribute("productPage", resultPage);
		
		return "admin/products/searchpaginated";
		
		
	}
	
}
