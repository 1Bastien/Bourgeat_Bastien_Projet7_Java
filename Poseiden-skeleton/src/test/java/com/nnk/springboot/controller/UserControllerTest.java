package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.service.MyUserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MyUserService myUserService;

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testHome() throws Exception {
		MyUser user = new MyUser();
		List<MyUser> users = new ArrayList<>();
		users.add(user);

		when(myUserService.getAllUsers(any(Model.class))).thenReturn("user/list");

		mockMvc.perform(get("/user/list").flashAttr("users", users)).andExpect(status().isOk())
				.andExpect(view().name("user/list")).andExpect(model().attributeExists("users"));

		verify(myUserService, times(1)).getAllUsers(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "USER")
	void testHomeForbidden() throws Exception {
		MyUser user = new MyUser();
		List<MyUser> users = new ArrayList<>();
		users.add(user);

		when(myUserService.getAllUsers(any(Model.class))).thenReturn("user/list");

		mockMvc.perform(get("/user/list").flashAttr("users", users)).andExpect(status().isForbidden());

		verify(myUserService, times(0)).getAllUsers(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testAddUserForm() throws Exception {
		MyUser user = new MyUser();

		when(myUserService.addUserForm(any(Model.class))).thenReturn("user/add");

		mockMvc.perform(get("/user/add").flashAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("user/add")).andExpect(model().attributeExists("user"));

		verify(myUserService, times(1)).addUserForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testValidate() throws Exception {
		MyUser user = new MyUser();
		user.setUsername("username");
		user.setFullname("fullname");
		user.setPassword("Password1!");
		user.setRole("role");

		when(myUserService.saveUser(any(MyUser.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/user/list");

		mockMvc.perform(post("/user/validate").with(csrf()).flashAttr("user", user))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/user/list"));

		verify(myUserService, times(1)).saveUser(eq(user), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testValidateWithErrors() throws Exception {
		MyUser user = new MyUser();

		mockMvc.perform(post("/user/validate").with(csrf()).flashAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("user/add"));

		verify(myUserService, never()).saveUser(eq(user), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testShowUpdateForm() throws Exception {
		MyUser user = new MyUser();

		when(myUserService.getUpdateForm(eq(1), any(Model.class))).thenReturn("user/update");

		mockMvc.perform(get("/user/update/1").flashAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("user/update")).andExpect(model().attributeExists("user"));

		verify(myUserService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testUpdateUser() throws Exception {
		MyUser user = new MyUser();
		user.setUsername("username");
		user.setFullname("fullname");
		user.setPassword("Password1!");
		user.setRole("role");

		when(myUserService.updateUser(eq(1), any(MyUser.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/user/list");

		mockMvc.perform(post("/user/update/1").with(csrf()).flashAttr("user", user))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/user/list"));

		verify(myUserService, times(1)).updateUser(eq(1), any(MyUser.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testUpdateUserWithErrors() throws Exception {
		MyUser user = new MyUser();

		mockMvc.perform(post("/user/update/1").with(csrf()).flashAttr("user", user)).andExpect(status().isOk())
				.andExpect(view().name("user/update"));

		verify(myUserService, never()).updateUser(eq(1), any(MyUser.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com", roles = "ADMIN")
	void testDeleteUser() throws Exception {
		when(myUserService.deleteUser(eq(1), any(RedirectAttributes.class))).thenReturn("redirect:/user/list");

		mockMvc.perform(get("/user/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/user/list"));

		verify(myUserService, times(1)).deleteUser(eq(1), any(RedirectAttributes.class));
	}
}
