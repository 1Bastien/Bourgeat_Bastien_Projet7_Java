package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.DTO.CurvePointDTO;

public interface CurvePointService {

	public String getAllCurvePoints(Model model);

	public String addCurvePointForm(Model model);

	public String saveCurvePoint(CurvePointDTO curvePointDTO, RedirectAttributes redirectAttributes);

	public String getUpdateForm(Integer id, Model model);

	public String updateCurvePoint(Integer id, CurvePointDTO curvePointDTO, RedirectAttributes redirectAttributes);

	public String deleteCurvePoint(Integer id, RedirectAttributes redirectAttributes);

}
