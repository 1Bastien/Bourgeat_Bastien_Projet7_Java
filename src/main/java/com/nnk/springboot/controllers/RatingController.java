package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.DTO.RatingDTO;
import com.nnk.springboot.service.RatingService;

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
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@GetMapping("/rating/list")
	public String home(Model model) {
		return ratingService.getAllRatings(model);
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Model model) {
		return ratingService.addRatingForm(model);
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid @ModelAttribute("rating") RatingDTO rating, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "rating/add";
		}
		return ratingService.saveRating(rating, redirectAttributes);
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		return ratingService.getUpdateForm(id, model);
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute("rating") RatingDTO rating,
			BindingResult result, RedirectAttributes redirectAttributes, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "rating/update";
		}
		return ratingService.updateRating(id, rating, redirectAttributes);
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		return ratingService.deleteRating(id, redirectAttributes);
	}
}
