package com.nnk.springboot.configuration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.repositories.MyUserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpringSecurityTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MyUserRepository myUserRepository;

	@BeforeEach
	public void setup() {
		myUserRepository.deleteAll();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		MyUser testUser = new MyUser();
		testUser.setUsername("testuser");
		testUser.setPassword(encoder.encode("Password1!"));
		testUser.setFullname("Test User");
		testUser.setRole("USER");
		myUserRepository.save(testUser);

	}

	@Test
	public void testSuccessfulLogin() throws Exception {
		mockMvc.perform(formLogin().user("testuser").password("Password1!")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void testFailedLogin() throws Exception {
		mockMvc.perform(formLogin().user("testuser").password("wrongpassword")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login?error"));
	}

}
