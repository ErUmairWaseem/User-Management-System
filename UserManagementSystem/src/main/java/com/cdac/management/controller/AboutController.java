package com.cdac.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {
	
	@RequestMapping(value = "/aboutus")
	public String about() {
		return "aboutus";
	}
}
