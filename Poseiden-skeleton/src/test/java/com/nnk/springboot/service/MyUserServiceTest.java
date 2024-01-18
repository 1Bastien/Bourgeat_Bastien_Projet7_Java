package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.repositories.MyUserRepository;
import com.nnk.springboot.service.impl.MyUserServiceImpl;

@ExtendWith(MockitoExtension.class)
class MyUserServiceTest {

	@InjectMocks
	private MyUserServiceImpl myUserService;

	@Mock
	private MyUserRepository myUserRepository;

	@Mock
	private Model model;

	@Test
	void testGetAllMyUsers() {
		MyUser user = new MyUser();
		List<MyUser> users = Arrays.asList(user);

		when(myUserRepository.findAll()).thenReturn(users);

		String resultView = myUserService.getAllUsers(model);

		verify(myUserRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("users"), anyList());
		verify(model).addAttribute("users", users);

		assertEquals("user/list", resultView);
	}

	@Test
	void testAddMyUserForm() {
		String resultView = myUserService.addUserForm(model);

		verify(model, times(1)).addAttribute(eq("user"), any(MyUser.class));
		verify(model).addAttribute(eq("user"), any(MyUser.class));

		assertEquals("user/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		MyUser sampleUser = new MyUser();

		when(myUserRepository.findById(anyInt())).thenReturn(Optional.of(sampleUser));

		String resultView = myUserService.getUpdateForm(1, model);

		verify(myUserRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("user"), same(sampleUser));

		assertEquals("user/update", resultView);
	}

	@Test
	void testGetUpdateFormMyUserNotFound() {
		when(myUserRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = myUserService.getUpdateForm(1, model);

		verify(myUserRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(MyUser.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("User not found"));

		assertEquals("redirect:/user/list", resultView);
	}

	@Test
	void testSaveMyUser() {
		MyUser user = new MyUser();
		user.setUsername("username");
		user.setPassword("Password1!");
		user.setFullname("fullname");
		user.setRole("USER");

		when(myUserRepository.save(any(MyUser.class))).thenReturn(user);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = myUserService.saveUser(user, redirectAttributes);

		verify(myUserRepository, times(1)).save(any(MyUser.class));

		assertEquals("User successfully added!", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/user/list", resultView);
	}

	@Test
	void testUpdateMyUserSuccess() {
		Integer userId = 1;
		MyUser user = new MyUser();
		user.setUsername("username");
		user.setPassword("Password1!");
		user.setFullname("fullname");
		user.setRole("USER");

		MyUser existingUser = new MyUser();

		when(myUserRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = myUserService.updateUser(userId, user, redirectAttributes);

		verify(myUserRepository, times(1)).save(existingUser);

		assertEquals("User successfully updated!", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/user/list", resultView);
	}

	@Test
	void testUpdateMyUserNotFound() {
		Integer userId = 1;
		MyUser user = new MyUser();

		when(myUserRepository.findById(userId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = myUserService.updateUser(userId, user, redirectAttributes);

		verify(myUserRepository, never()).save(any());

		assertEquals("User not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/user/list", resultView);
	}

	@Test
	void testDeleteMyUserSuccess() {
		MyUser bidList = new MyUser();

		when(myUserRepository.findById(1)).thenReturn(Optional.of(bidList));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = myUserService.deleteUser(1, redirectAttributes);

		verify(myUserRepository, times(1)).deleteById(1);

		assertEquals("User successfully deleted!", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/user/list", resultView);
	}

	@Test
	void testDeleteMyUserNotFound() {
		when(myUserRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = myUserService.deleteUser(1, redirectAttributes);

		verify(myUserRepository, never()).deleteById(anyInt());

		assertEquals("User not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/user/list", resultView);
	}
}