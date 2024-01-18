package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.repositories.MyUserRepository;
import com.nnk.springboot.service.MyUserService;

@Service
public class MyUserServiceImpl implements MyUserService {

	@Autowired
	private MyUserRepository myUserRepository;

	public String getAllUsers(Model model) {
		try {
			List<MyUser> users = myUserRepository.findAll();
			model.addAttribute("users", users);
		} catch (Exception e) {
			throw new RuntimeException("Error while getting all users");
		}
		return "user/list";
	}

	public String addUserForm(Model model) {
		try {
			MyUser myUser = new MyUser();
			model.addAttribute("user", myUser);
		} catch (Exception e) {
			throw new RuntimeException("Error while adding user");
		}
		return "user/add";
	}

	public String saveUser(MyUser user, RedirectAttributes redirectAttributes) {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			myUserRepository.save(user);
			redirectAttributes.addFlashAttribute("success", "User successfully added!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error while adding user");
		}
		return "redirect:/user/list";
	}

	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<MyUser> user = myUserRepository.findById(id);
			if (user.isPresent()) {
				model.addAttribute("user", user.get());
			} else {
				model.addAttribute("error", "User not found");
				return "redirect:/user/list";
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while showing update form");
		}
		return "user/update";
	}

	public String updateUser(Integer id, MyUser user, RedirectAttributes redirectAttributes) {
		try {
			Optional<MyUser> myUserToUpdate = myUserRepository.findById(id);
			if (myUserToUpdate.isPresent()) {
				MyUser oldUser = myUserToUpdate.get();
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				oldUser.setUsername(user.getUsername());
				oldUser.setFullname(user.getFullname());
				oldUser.setRole(user.getRole());
				oldUser.setPassword(encoder.encode(user.getPassword()));
				myUserRepository.save(oldUser);
				redirectAttributes.addFlashAttribute("success", "User successfully updated!");
			} else {
				redirectAttributes.addFlashAttribute("error", "User not found");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error while updating user");
		}
		return "redirect:/user/list";
	}

	public String deleteUser(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<MyUser> user = myUserRepository.findById(id);
			if (user.isPresent()) {
				myUserRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "User successfully deleted!");
			} else {
				redirectAttributes.addFlashAttribute("error", "User not found");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while deleting all users");
		}
		return "redirect:/user/list";
	}
}
