package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DTO.TradeDTO;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@GetMapping("/trade/list")
	public String home(Model model) {
		return tradeService.getAllTrades(model);
	}

	@GetMapping("/trade/add")
	public String addUser(Model model) {
		return tradeService.addTradeForm(model);
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid @ModelAttribute("trade") TradeDTO trade, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "trade/add";
		}
		return tradeService.saveTrade(trade, redirectAttributes);
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return tradeService.getUpdateForm(id, model);
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute("trade") TradeDTO trade,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "trade/update";
		}

		return tradeService.updateTrade(id, trade, redirectAttributes);
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return tradeService.deleteTrade(id, redirectAttributes);
	}
}
