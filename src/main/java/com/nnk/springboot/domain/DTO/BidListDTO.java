package com.nnk.springboot.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BidListDTO {

	@NotNull
	@NotBlank(message = "Account is mandatory")
	@Size(min = 1, max = 30, message = "Account must be between 1 and 30 characters")
	private String account;

	@NotNull
	@NotBlank(message = "Type is mandatory")
	@Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters")
	private String type;

	private Double bidQuantity;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBidQuantity() {
		return bidQuantity;
	}

	public void setBidQuantity(Double bidQuantity) {
		this.bidQuantity = bidQuantity;
	}
}
