package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DTO.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

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
public class CurveController {

	@Autowired
	private CurvePointService curvePointService;

	@GetMapping("/curvePoint/list")
	public String home(Model model) {
		return curvePointService.getAllCurvePoints(model);
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(Model model) {
		return curvePointService.addCurvePointForm(model);
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid @ModelAttribute("curvePoint") CurvePointDTO curvePoint, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}
		return curvePointService.saveCurvePoint(curvePoint, redirectAttributes);
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return curvePointService.getUpdateForm(id, model);
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id,
			@Valid @ModelAttribute("curvePoint") CurvePointDTO curvePoint, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "curvePoint/update";
		}
		return curvePointService.updateCurvePoint(id, curvePoint, redirectAttributes);
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return curvePointService.deleteCurvePoint(id, redirectAttributes);
	}
}
