package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.DTO.RatingDTO;
import com.nnk.springboot.service.RatingService;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingService ratingService;

	@Test
	@WithMockUser(username = "john@test.com")
	void testHome() throws Exception {
		Rating rating = new Rating();
		List<Rating> ratings = new ArrayList<>();
		ratings.add(rating);

		when(ratingService.getAllRatings(any(Model.class))).thenReturn("rating/list");

		mockMvc.perform(get("/rating/list").flashAttr("ratings", ratings)).andExpect(status().isOk())
				.andExpect(view().name("rating/list")).andExpect(model().attributeExists("ratings"));

		verify(ratingService, times(1)).getAllRatings(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testaddRatingForm() throws Exception {
		Rating rating = new Rating();

		when(ratingService.addRatingForm(any(Model.class))).thenReturn("rating/add");

		mockMvc.perform(get("/rating/add").flashAttr("rating", rating)).andExpect(status().isOk())
				.andExpect(view().name("rating/add")).andExpect(model().attributeExists("rating"));

		verify(ratingService, times(1)).addRatingForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidate() throws Exception {
		RatingDTO rating = new RatingDTO();
		rating.setMoodysRating("MoodysRating");
		rating.setSandPRating("SandPRating");
		rating.setFitchRating("FitchRating");
		rating.setOrderNumber(10);

		when(ratingService.saveRating(any(RatingDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/rating/list");

		mockMvc.perform(post("/rating/validate").with(csrf()).flashAttr("rating", rating))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/rating/list"));

		verify(ratingService, times(1)).saveRating(eq(rating), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidateWithErrors() throws Exception {
		RatingDTO rating = new RatingDTO();
		rating.setMoodysRating(
				"MoodysRatingxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/rating/validate").with(csrf()).flashAttr("rating", rating)).andExpect(status().isOk())
				.andExpect(view().name("rating/add"));

		verify(ratingService, never()).saveRating(eq(rating), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testShowUpdateForm() throws Exception {
		Rating rating = new Rating();

		when(ratingService.getUpdateForm(eq(1), any(Model.class))).thenReturn("rating/update");

		mockMvc.perform(get("/rating/update/1").flashAttr("rating", rating)).andExpect(status().isOk())
				.andExpect(view().name("rating/update")).andExpect(model().attributeExists("rating"));

		verify(ratingService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateRating() throws Exception {
		RatingDTO rating = new RatingDTO();
		rating.setMoodysRating("MoodysRating");
		rating.setSandPRating("SandPRating");
		rating.setFitchRating("FitchRating");
		rating.setOrderNumber(10);

		when(ratingService.updateRating(eq(1), any(RatingDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/rating/list");

		mockMvc.perform(post("/rating/update/1").with(csrf()).flashAttr("rating", rating))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/rating/list"));

		verify(ratingService, times(1)).updateRating(eq(1), any(RatingDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateRatingWithErrors() throws Exception {
		RatingDTO rating = new RatingDTO();
		rating.setMoodysRating(
				"MoodysRatingxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
						+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/rating/update/1").with(csrf()).flashAttr("rating", rating)).andExpect(status().isOk())
				.andExpect(view().name("rating/update"));

		verify(ratingService, never()).updateRating(eq(1), any(RatingDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testDeleteRating() throws Exception {
		when(ratingService.deleteRating(eq(1), any(RedirectAttributes.class))).thenReturn("redirect:/rating/list");

		mockMvc.perform(get("/rating/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rating/list"));

		verify(ratingService, times(1)).deleteRating(eq(1), any(RedirectAttributes.class));
	}
}
