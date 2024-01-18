package com.nnk.springboot.domain.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TradeDTO {

	@NotNull
	@Size(max = 30, message = "Account is limited to 30 characters")
	private String account;

	@NotNull
	@Size(max = 30, message = "Type is limited to 30 characters")
	private String type;

	private Double buyQuantity;

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

	public Double getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
}
