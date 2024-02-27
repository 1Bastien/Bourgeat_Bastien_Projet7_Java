package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("unauthorized")
	public String error(Model model) {
		String errorMessage = "You are not authorized for the requested data.";
		model.addAttribute("errorMsg", errorMessage);
		return "403";
	}
}
