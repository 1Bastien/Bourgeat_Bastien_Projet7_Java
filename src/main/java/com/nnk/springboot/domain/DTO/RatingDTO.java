package com.nnk.springboot.domain.DTO;

import jakarta.validation.constraints.Size;

public class RatingDTO {

	@Size(max = 125, message = "moodysRating is limited to 125 characters")
	private String moodysRating;

	@Size(max = 125, message = "sandPRating is limited to 125 characters")
	private String sandPRating;

	@Size(max = 125, message = "fitchRating is limited to 125 characters")
	private String fitchRating;

	private Integer orderNumber;

	public String getMoodysRating() {
		return moodysRating;
	}

	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}

	public String getSandPRating() {
		return sandPRating;
	}

	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}

	public String getFitchRating() {
		return fitchRating;
	}

	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
}
