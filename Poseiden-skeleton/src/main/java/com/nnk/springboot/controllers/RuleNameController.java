package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DTO.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

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
public class RuleNameController {

	@Autowired
	private RuleNameService ruleNameService;

	@GetMapping("/ruleName/list")
	public String home(Model model) {
		return ruleNameService.getAllRuleNames(model);
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(Model model) {
		return ruleNameService.addRuleNameForm(model);
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid @ModelAttribute("ruleName") RuleNameDTO ruleName, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "ruleName/add";
		}

		return ruleNameService.saveRuleName(ruleName, redirectAttributes);
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return ruleNameService.getUpdateForm(id, model);
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("ruleName") RuleNameDTO ruleName, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "ruleName/update";
		}

		return ruleNameService.updateRuleName(id, ruleName, redirectAttributes);
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return ruleNameService.deleteRuleName(id, redirectAttributes);
	}
}
