package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.DTO.RuleNameDTO;
import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;

@Service
public class RuleNameServiceImpl implements RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private RuleNameMapper ruleNameMapper;

	@Transactional(readOnly = true)
	public String getAllRuleNames(Model model) {
		try {
			List<RuleName> ruleNames = ruleNameRepository.findAll();
			model.addAttribute("ruleNames", ruleNames);
		} catch (Exception e) {
			throw new RuntimeException("RuleName not found");
		}
		return "ruleName/list";
	}

	public String addRuleNameForm(Model model) {
		try {
			RuleName ruleName = new RuleName();
			model.addAttribute("ruleName", ruleName);
		} catch (Exception e) {
			throw new RuntimeException("Error while loading the form");
		}
		return "ruleName/add";
	}

	@Transactional
	public String saveRuleName(RuleNameDTO ruleNameDTO, RedirectAttributes redirectAttributes) {
		try {
			RuleName ruleName = new RuleName();

			ruleNameMapper.updateRuleNameFromDto(ruleNameDTO, ruleName);

			ruleNameRepository.save(ruleName);
			redirectAttributes.addFlashAttribute("success", "RuleName successfully added");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error while saving RuleName");
		}
		return "redirect:/ruleName/list";
	}

	@Transactional(readOnly = true)
	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);

			if (optionalRuleName.isPresent()) {
				model.addAttribute("ruleName", optionalRuleName.get());
			} else {
				model.addAttribute("error", "No RuleName with this id");
				return "redirect:/ruleName/list";
			}

		} catch (Exception e) {
			throw new RuntimeException("Error while loading the form");
		}
		return "ruleName/update";
	}

	@Transactional
	public String updateRuleName(Integer id, RuleNameDTO ruleNameDTO, RedirectAttributes redirectAttributes) {
		try {
			Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
			if (optionalRuleName.isPresent()) {

				RuleName oldRuleName = optionalRuleName.get();

				ruleNameMapper.updateRuleNameFromDto(ruleNameDTO, oldRuleName);

				ruleNameRepository.save(oldRuleName);
				redirectAttributes.addFlashAttribute("success", "RuleName successfully updated");

			} else {
				redirectAttributes.addFlashAttribute("error", "No RuleName with this id");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error while updating RuleName");
		}

		return "redirect:/ruleName/list";
	}

	@Transactional
	public String deleteRuleName(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<RuleName> optionalRuleName = ruleNameRepository.findById(id);
			if (optionalRuleName.isPresent()) {
				ruleNameRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "RuleName successfully deleted");
			} else {
				redirectAttributes.addFlashAttribute("error", "No RuleName with this id");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while deleting RuleName");
		}
		return "redirect:/ruleName/list";
	}

}
