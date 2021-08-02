package edu.poly.shop.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


import edu.poly.shop.domain.Account;

import edu.poly.shop.model.AccountDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.utils.SessionService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	SessionService session;

	@RequestMapping("new")
	public String index(Model model) {
		List<Account> list = accountService.findAll();
		model.addAttribute("accounts", list);

		AccountDto account = new AccountDto();
		model.addAttribute("account", account);

		model.addAttribute("USERLOGIN", session.get("USERLOGIN"));

		return "admin/accounts/addOrEdit";
	}

	@ModelAttribute("getRole")
	public Map<Integer, String> getRole() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Admin");
		map.put(2, "User");
		return map;
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,@Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
		
		if (result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
		}
		Account emtity = new Account();
		BeanUtils.copyProperties(dto, emtity);
		accountService.save(emtity);
		model.addAttribute("message", "Lưu tài khoản thành công");

		return new ModelAndView("forward:/admin/accounts/new", model);
	}

	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
		Optional<Account> opt = accountService.findById(username);
		AccountDto dto = new AccountDto();

		if (opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("account", dto);

			return new ModelAndView("admin/accounts/addOrEdit", model);
		}
		model.addAttribute("mesage", "Không tìm thấy tài khoản");
		return new ModelAndView("forward:/admin/accounts/addOrEdit", model);
	}

	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
		accountService.deleteById(username);
		model.addAttribute("message", "Xóa tài khoản thành công");

		return new ModelAndView("forward:/admin/accounts/searchpaginated", model);
	}

	
	@GetMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "username", required = false) String username,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size

	) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("username"));
		Page<Account> resultPage = null;

		if (StringUtils.hasText(username)) {
			resultPage = accountService.findByUsernameContaining(username, pageable);
			model.addAttribute("name", username);
		} else {
			resultPage = accountService.findAll(pageable);
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

		model.addAttribute("name", username);
		model.addAttribute("accountPage", resultPage);

		return "admin/accounts/searchpaginated";

	}
}
