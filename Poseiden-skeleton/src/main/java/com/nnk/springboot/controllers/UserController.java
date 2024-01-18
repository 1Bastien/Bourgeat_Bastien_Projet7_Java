package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.service.MyUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private MyUserService myUserService;

	@GetMapping("/user/list")
	public String home(Model model) {
		return myUserService.getAllUsers(model);
	}

	@GetMapping("/user/add")
	public String addUser(Model model) {
		return myUserService.addUserForm(model);
	}

	@PostMapping("/user/validate")
	public String validate(@Valid @ModelAttribute("user") MyUser user, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "user/add";
		}
		return myUserService.saveUser(user, redirectAttributes);
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return myUserService.getUpdateForm(id, model);
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") MyUser user,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "user/update";
		}
		return myUserService.updateUser(id, user, redirectAttributes);
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return myUserService.deleteUser(id, redirectAttributes);
	}
}
