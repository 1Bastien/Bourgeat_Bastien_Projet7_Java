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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.DTO.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@WebMvcTest(RuleNameController.class)
class RuleNameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuleNameService ruleNameService;

	@Test
	@WithMockUser(username = "john@test.com")
	void testHome() throws Exception {
		RuleName ruleName = new RuleName();
		List<RuleName> ruleNames = new ArrayList<>();
		ruleNames.add(ruleName);

		when(ruleNameService.getAllRuleNames(any(Model.class))).thenReturn("ruleName/list");

		mockMvc.perform(get("/ruleName/list").flashAttr("ruleNames", ruleNames)).andExpect(status().isOk())
				.andExpect(view().name("ruleName/list")).andExpect(model().attributeExists("ruleNames"));

		verify(ruleNameService, times(1)).getAllRuleNames(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testaddRuleNameForm() throws Exception {
		RuleName ruleName = new RuleName();

		when(ruleNameService.addRuleNameForm(any(Model.class))).thenReturn("ruleName/add");

		mockMvc.perform(get("/ruleName/add").flashAttr("ruleName", ruleName)).andExpect(status().isOk())
				.andExpect(view().name("ruleName/add")).andExpect(model().attributeExists("ruleName"));

		verify(ruleNameService, times(1)).addRuleNameForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidate() throws Exception {
		RuleNameDTO ruleName = new RuleNameDTO();
		ruleName.setName("Name");
		ruleName.setDescription("Description");
		ruleName.setJson("Json");
		ruleName.setTemplate("Template");
		ruleName.setSqlStr("SqlStr");
		ruleName.setSqlPart("SqlPart");

		when(ruleNameService.saveRuleName(any(RuleNameDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/ruleName/list");

		mockMvc.perform(post("/ruleName/validate").with(csrf()).flashAttr("ruleName", ruleName))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/ruleName/list"));

		verify(ruleNameService, times(1)).saveRuleName(eq(ruleName), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidateWithErrors() throws Exception {
		RuleNameDTO ruleName = new RuleNameDTO();
		ruleName.setName(
				"Namexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/ruleName/validate").with(csrf()).flashAttr("ruleName", ruleName))
				.andExpect(status().isOk()).andExpect(view().name("ruleName/add"));

		verify(ruleNameService, never()).saveRuleName(eq(ruleName), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testShowUpdateForm() throws Exception {
		RuleName ruleName = new RuleName();

		when(ruleNameService.getUpdateForm(eq(1), any(Model.class))).thenReturn("ruleName/update");

		mockMvc.perform(get("/ruleName/update/1").flashAttr("ruleName", ruleName)).andExpect(status().isOk())
				.andExpect(view().name("ruleName/update")).andExpect(model().attributeExists("ruleName"));

		verify(ruleNameService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateRuleName() throws Exception {
		RuleNameDTO ruleName = new RuleNameDTO();
		ruleName.setName("Name");
		ruleName.setDescription("Description");
		ruleName.setJson("Json");
		ruleName.setTemplate("Template");
		ruleName.setSqlStr("SqlStr");
		ruleName.setSqlPart("SqlPart");

		when(ruleNameService.updateRuleName(eq(1), any(RuleNameDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/ruleName/list");

		mockMvc.perform(post("/ruleName/update/1").with(csrf()).flashAttr("ruleName", ruleName))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/ruleName/list"));

		verify(ruleNameService, times(1)).updateRuleName(eq(1), any(RuleNameDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateRuleNameWithErrors() throws Exception {
		RuleNameDTO ruleName = new RuleNameDTO();
		ruleName.setName(
				"Namexxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/ruleName/update/1").with(csrf()).flashAttr("ruleName", ruleName))
				.andExpect(status().isOk()).andExpect(view().name("ruleName/update"));

		verify(ruleNameService, never()).updateRuleName(eq(1), any(RuleNameDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testDeleteRuleName() throws Exception {
		when(ruleNameService.deleteRuleName(eq(1), any(RedirectAttributes.class)))
				.thenReturn("redirect:/ruleName/list");

		mockMvc.perform(get("/ruleName/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/ruleName/list"));

		verify(ruleNameService, times(1)).deleteRuleName(eq(1), any(RedirectAttributes.class));
	}
}
