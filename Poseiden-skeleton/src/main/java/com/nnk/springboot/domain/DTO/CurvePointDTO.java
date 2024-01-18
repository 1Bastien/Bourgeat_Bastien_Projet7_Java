package com.nnk.springboot.domain.DTO;

import jakarta.validation.constraints.NotNull;

public class CurvePointDTO {

	@NotNull
	private Integer curveId;

	private Double term;

	private Double value;

	public Integer getCurveId() {
		return curveId;
	}

	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}

	public Double getTerm() {
		return term;
	}

	public void setTerm(Double term) {
		this.term = term;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
