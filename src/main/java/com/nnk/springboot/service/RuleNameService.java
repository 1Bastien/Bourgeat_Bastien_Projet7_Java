package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.DTO.RuleNameDTO;

public interface RuleNameService {
	public String getAllRuleNames(Model model);

	public String addRuleNameForm(Model model);

	public String saveRuleName(RuleNameDTO ruleNameDTO, RedirectAttributes redirectAttributes);

	public String getUpdateForm(Integer id, Model model);

	public String updateRuleName(Integer id, RuleNameDTO ruleNameDTO, RedirectAttributes redirectAttributes);

	public String deleteRuleName(Integer id, RedirectAttributes redirectAttributes);

}
