package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.DTO.RatingDTO;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private RatingMapper ratingMapper;

	@Transactional(readOnly = true)
	public String getAllRatings(Model model) {
		try {
			List<Rating> ratings = ratingRepository.findAll();
			model.addAttribute("ratings", ratings);
		} catch (Exception e) {
			throw new RuntimeException("Rating not found");
		}
		return "rating/list";
	}

	public String addRatingForm(Model model) {
		try {
			Rating rating = new Rating();
			model.addAttribute("rating", rating);
		} catch (Exception e) {
			throw new RuntimeException("Error getting rating form");
		}

		return "rating/add";
	}

	@Transactional
	public String saveRating(RatingDTO ratingDTO, RedirectAttributes redirectAttributes) {
		try {
			Rating rating = new Rating();

			ratingMapper.updateRatingFromDto(ratingDTO, rating);

			ratingRepository.save(rating);
			redirectAttributes.addFlashAttribute("success", "Rating successfully added");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error adding rating");
		}

		return "redirect:/rating/list";
	}

	@Transactional(readOnly = true)
	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<Rating> rating = ratingRepository.findById(id);

			if (rating.isPresent()) {
				model.addAttribute("rating", rating.get());
			} else {
				model.addAttribute("error", "Rating not found");
				return "redirect:/rating/list";
			}

		} catch (Exception e) {
			throw new RuntimeException("Error getting rating form");
		}

		return "rating/update";
	}

	@Transactional
	public String updateRating(Integer id, RatingDTO ratingDTO, RedirectAttributes redirectAttributes) {
		try {
			Optional<Rating> ratingToUpdate = ratingRepository.findById(id);
			if (ratingToUpdate.isPresent()) {

				Rating updatedRating = ratingToUpdate.get();

				ratingMapper.updateRatingFromDto(ratingDTO, updatedRating);

				ratingRepository.save(updatedRating);
				redirectAttributes.addFlashAttribute("success", "Rating successfully updated");

			} else {
				redirectAttributes.addFlashAttribute("error", "Rating not found");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating rating");
		}

		return "redirect:/rating/list";
	}

	@Transactional
	public String deleteRating(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<Rating> rating = ratingRepository.findById(id);
			if (rating.isPresent()) {
				ratingRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "Rating successfully deleted");
			} else {
				redirectAttributes.addFlashAttribute("error", "Rating not found");
			}

		} catch (Exception e) {
			throw new RuntimeException("Error deleting rating");
		}

		return "redirect:/rating/list";
	}
}
