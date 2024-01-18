package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DTO.BidListDTO;
import com.nnk.springboot.service.BidListService;

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
public class BidListController {

	@Autowired
	private BidListService bidListService;

	@GetMapping("/bidList/list")
	public String home(Model model) {
		return bidListService.getAllBidLists(model);
	}

	@GetMapping("/bidList/add")
	public String addBidForm(Model model) {
		return bidListService.addBidListForm(model);
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid @ModelAttribute("bidList") BidListDTO bidList, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "bidList/add";
		}
		return bidListService.saveBidList(bidList, redirectAttributes);
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return bidListService.getUpdateForm(id, model);
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidListDTO bidList,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "bidList/update";
		}
		return bidListService.updateBidList(id, bidList, redirectAttributes);
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return bidListService.deleteBid(id, redirectAttributes);
	}
}
