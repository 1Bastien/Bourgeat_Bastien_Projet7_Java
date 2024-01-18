package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.DTO.RatingDTO;

public interface RatingService {

	public String getAllRatings(Model model);

	public String addRatingForm(Model model);

	public String saveRating(RatingDTO ratingDTO, RedirectAttributes redirectAttributes);

	public String getUpdateForm(Integer id, Model model);

	public String updateRating(Integer id, RatingDTO ratingDTO, RedirectAttributes redirectAttributes);

	public String deleteRating(Integer id, RedirectAttributes redirectAttributes);

}
