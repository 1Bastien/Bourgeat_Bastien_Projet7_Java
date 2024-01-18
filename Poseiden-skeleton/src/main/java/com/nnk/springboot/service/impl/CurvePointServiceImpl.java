package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.DTO.CurvePointDTO;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

@Service
public class CurvePointServiceImpl implements CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	private CurvePointMapper curvePointMapper;

	@Transactional(readOnly = true)
	public String getAllCurvePoints(Model model) {
		try {
			List<CurvePoint> curvePoints = curvePointRepository.findAll();
			model.addAttribute("curvePoints", curvePoints);
		} catch (Exception e) {
			throw new RuntimeException("CurvePoint not found");
		}
		return "curvePoint/list";
	}

	public String addCurvePointForm(Model model) {
		try {
			CurvePoint curvePoint = new CurvePoint();
			model.addAttribute("curvePoint", curvePoint);
		} catch (Exception e) {
			throw new RuntimeException("Error getting curvePoint form");
		}

		return "curvePoint/add";
	}

	@Transactional
	public String saveCurvePoint(CurvePointDTO curvePointDTO, RedirectAttributes redirectAttributes) {
		try {
			CurvePoint curvePoint = new CurvePoint();

			curvePointMapper.updateCurvePointFromDto(curvePointDTO, curvePoint);

			curvePointRepository.save(curvePoint);
			redirectAttributes.addFlashAttribute("success", "CurvePoint successfully added");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error adding curvePoint");
		}
		return "redirect:/curvePoint/list";
	}

	@Transactional(readOnly = true)
	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);

			if (curvePoint.isPresent()) {
				model.addAttribute("curvePoint", curvePoint.get());
			} else {
				model.addAttribute("error", "CurvePoint not found");
				return "redirect:/curvePoint/list";
			}
		} catch (Exception e) {
			throw new RuntimeException("Error getting curvePoint form");
		}

		return "curvePoint/update";
	}

	@Transactional
	public String updateCurvePoint(Integer id, CurvePointDTO curvePointDTO, RedirectAttributes redirectAttributes) {
		try {
			Optional<CurvePoint> curvePointToUpdate = curvePointRepository.findById(id);
			if (curvePointToUpdate.isPresent()) {

				CurvePoint curvePoint = curvePointToUpdate.get();

				curvePointMapper.updateCurvePointFromDto(curvePointDTO, curvePoint);

				curvePointRepository.save(curvePoint);
				redirectAttributes.addFlashAttribute("success", "CurvePoint successfully updated");
			} else {
				redirectAttributes.addFlashAttribute("error", "CurvePoint not found");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating curvePoint");
		}
		return "redirect:/curvePoint/list";
	}

	@Transactional
	public String deleteCurvePoint(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
			if (curvePoint.isPresent()) {
				curvePointRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "CurvePoint successfully deleted");
			} else {
				redirectAttributes.addFlashAttribute("error", "CurvePoint not found");
			}

		} catch (Exception e) {
			throw new RuntimeException("Error deleting CurvePoint");
		}
		return "redirect:/curvePoint/list";
	}
}
