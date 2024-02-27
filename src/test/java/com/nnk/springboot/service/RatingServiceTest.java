package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.DTO.RatingDTO;
import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.impl.RatingServiceImpl;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@InjectMocks
	private RatingServiceImpl ratingService;

	@Mock
	private RatingRepository ratingRepository;

	@Mock
	private Model model;

	@Mock
	private RatingMapper ratingMapper;

	@Test
	void testGetAllRatings() {
		Rating rating = new Rating();
		List<Rating> ratings = Arrays.asList(rating);

		when(ratingRepository.findAll()).thenReturn(ratings);

		String resultView = ratingService.getAllRatings(model);

		verify(ratingRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("ratings"), anyList());
		verify(model).addAttribute("ratings", ratings);

		assertEquals("rating/list", resultView);
	}

	@Test
	void testAddRatingForm() {
		String resultView = ratingService.addRatingForm(model);

		verify(model, times(1)).addAttribute(eq("rating"), any(Rating.class));
		verify(model).addAttribute(eq("rating"), any(Rating.class));

		assertEquals("rating/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		Rating sampleRating = new Rating();

		when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(sampleRating));

		String resultView = ratingService.getUpdateForm(1, model);

		verify(ratingRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("rating"), same(sampleRating));

		assertEquals("rating/update", resultView);
	}

	@Test
	void testGetUpdateFormRatingNotFound() {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = ratingService.getUpdateForm(1, model);

		verify(ratingRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(Rating.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("Rating not found"));

		assertEquals("redirect:/rating/list", resultView);
	}

	@Test
	void testSaveRatingList() {
		RatingDTO ratingDTO = new RatingDTO();

		Rating rating = new Rating();

		when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ratingService.saveRating(ratingDTO, redirectAttributes);

		verify(ratingMapper, times(1)).updateRatingFromDto(eq(ratingDTO), any(Rating.class));
		verify(ratingRepository, times(1)).save(any(Rating.class));

		assertEquals("Rating successfully added", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/rating/list", resultView);
	}

	@Test
	void testUpdateRatingListSuccess() {
		Integer ratingId = 1;
		RatingDTO ratingDTO = new RatingDTO();

		Rating existingBidList = new Rating();

		when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(existingBidList));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ratingService.updateRating(ratingId, ratingDTO, redirectAttributes);

		verify(ratingMapper, times(1)).updateRatingFromDto(ratingDTO, existingBidList);
		verify(ratingRepository, times(1)).save(existingBidList);

		assertEquals("Rating successfully updated", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/rating/list", resultView);
	}

	@Test
	void testUpdateRatingNotFound() {
		Integer ratingId = 1;
		RatingDTO ratingDTO = new RatingDTO();

		when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ratingService.updateRating(ratingId, ratingDTO, redirectAttributes);

		verify(ratingMapper, never()).updateRatingFromDto(any(), any());
		verify(ratingRepository, never()).save(any());

		assertEquals("Rating not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/rating/list", resultView);
	}

	@Test
	void testDeleteRatingSuccess() {
		Rating rating = new Rating();

		when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ratingService.deleteRating(1, redirectAttributes);

		verify(ratingRepository, times(1)).deleteById(1);

		assertEquals("Rating successfully deleted", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/rating/list", resultView);
	}

	@Test
	void testDeleteRatingNotFound() {
		when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = ratingService.deleteRating(1, redirectAttributes);

		verify(ratingRepository, never()).deleteById(anyInt());

		assertEquals("Rating not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/rating/list", resultView);
	}
}