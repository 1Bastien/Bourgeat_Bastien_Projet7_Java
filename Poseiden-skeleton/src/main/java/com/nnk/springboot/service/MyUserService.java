package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.MyUser;

public interface MyUserService {

	public String getAllUsers(Model model);

	public String addUserForm(Model model);

	public String saveUser(MyUser user, RedirectAttributes redirectAttributes);

	public String getUpdateForm(Integer id, Model model);

	public String updateUser(Integer id, MyUser user, RedirectAttributes redirectAttributes);

	public String deleteUser(Integer id, RedirectAttributes redirectAttributes);

}
