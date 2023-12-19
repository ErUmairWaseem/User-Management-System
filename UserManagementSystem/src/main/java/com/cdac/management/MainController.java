package com.cdac.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cdac.management.entity.User;
import com.cdac.management.services.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	
	
	
	// GET INDEX PAGE
	@GetMapping("")
	public String getIndex(User user, Model model) {
		
		
		model.addAttribute("roleSize",userService.listAllRoles().size());
		model.addAttribute("allRoles",userService.listAllRoles());
		model.addAttribute("userSize",userService.listAllUsers().size());
		model.addAttribute("allUsers",userService.listAllUsers());
		
		return "index";
	}
	

	// login
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	
		
	
}