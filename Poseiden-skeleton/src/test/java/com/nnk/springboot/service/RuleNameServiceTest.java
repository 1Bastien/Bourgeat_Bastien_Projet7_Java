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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.DTO.RuleNameDTO;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.impl.RuleNameServiceImpl;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

	@InjectMocks
	private RuleNameServiceImpl ruleNameService;

	@Mock
	private RuleNameRepository ruleNameRepository;

	@Mock
	private Model model;

	@Mock
	private RuleNameMapper ruleNameMapper;

	@Test
	void testGetAllRuleNames() {
		RuleName ruleName = new RuleName();
		List<RuleName> ruleNames = Arrays.asList(ruleName);

		when(ruleNameRepository.findAll()).thenReturn(ruleNames);

		String resultView = ruleNameService.getAllRuleNames(model);

		verify(ruleNameRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("ruleNames"), anyList());
		verify(model).addAttribute("ruleNames", ruleNames);

		assertEquals("ruleName/list", resultView);
	}

	@Test
	void testAddRuleNameForm() {
		String resultView = ruleNameService.addRuleNameForm(model);

		verify(model, times(1)).addAttribute(eq("ruleName"), any(RuleName.class));
		verify(model).addAttribute(eq("ruleName"), any(RuleName.class));

		assertEquals("ruleName/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		RuleName ruleName = new RuleName();

		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

		String resultView = ruleNameService.getUpdateForm(1, model);

		verify(ruleNameRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("ruleName"), same(ruleName));

		assertEquals("ruleName/update", resultView);
	}

	@Test
	void testGetUpdateFormRuleNameNotFound() {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = ruleNameService.getUpdateForm(1, model);

		verify(ruleNameRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(RuleName.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("No RuleName with this id"));

		assertEquals("redirect:/ruleName/list", resultView);
	}

	@Test
	void testSaveRuleName() {
		RuleNameDTO ruleNameDTO = new RuleNameDTO();

		RuleName ruleName = new RuleName();

		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ruleNameService.saveRuleName(ruleNameDTO, redirectAttributes);

		verify(ruleNameMapper, times(1)).updateRuleNameFromDto(eq(ruleNameDTO), any(RuleName.class));
		verify(ruleNameRepository, times(1)).save(any(RuleName.class));

		assertEquals("RuleName successfully added", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/ruleName/list", resultView);
	}

	@Test
	void testUpdateRuleNameSuccess() {
		Integer ruleNameId = 1;
		RuleNameDTO ruleNameDTO = new RuleNameDTO();

		RuleName existingRuleName = new RuleName();

		when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.of(existingRuleName));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ruleNameService.updateRuleName(ruleNameId, ruleNameDTO, redirectAttributes);

		verify(ruleNameMapper, times(1)).updateRuleNameFromDto(ruleNameDTO, existingRuleName);
		verify(ruleNameRepository, times(1)).save(existingRuleName);

		assertEquals("RuleName successfully updated", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/ruleName/list", resultView);
	}

	@Test
	void testUpdateRuleNameNotFound() {
		Integer ruleNameId = 1;
		RuleNameDTO ruleNameDTO = new RuleNameDTO();

		when(ruleNameRepository.findById(ruleNameId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ruleNameService.updateRuleName(ruleNameId, ruleNameDTO, redirectAttributes);

		verify(ruleNameMapper, never()).updateRuleNameFromDto(any(), any());
		verify(ruleNameRepository, never()).save(any());

		assertEquals("No RuleName with this id", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/ruleName/list", resultView);
	}

	@Test
	void testDeleteRuleNameSuccess() {
		RuleName ruleName = new RuleName();

		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ruleNameService.deleteRuleName(1, redirectAttributes);

		verify(ruleNameRepository, times(1)).deleteById(1);

		assertEquals("RuleName successfully deleted", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/ruleName/list", resultView);
	}

	@Test
	void testDeleteRuleNameNotFound() {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ruleNameService.deleteRuleName(1, redirectAttributes);

		verify(ruleNameRepository, never()).deleteById(anyInt());

		assertEquals("No RuleName with this id", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/ruleName/list", resultView);
	}
}