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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.DTO.CurvePointDTO;
import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.impl.CurvePointServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

	@InjectMocks
	private CurvePointServiceImpl curvePointService;

	@Mock
	private CurvePointRepository curvePointRepository;

	@Mock
	private Model model;

	@Mock
	private CurvePointMapper curvePointMapper;

	@Test
	void testGetAllCurvePoints() {
		CurvePoint curvePoint = new CurvePoint();
		List<CurvePoint> curvePoints = Arrays.asList(curvePoint);

		when(curvePointRepository.findAll()).thenReturn(curvePoints);

		String resultView = curvePointService.getAllCurvePoints(model);

		verify(curvePointRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("curvePoints"), anyList());
		verify(model).addAttribute("curvePoints", curvePoints);

		assertEquals("curvePoint/list", resultView);
	}

	@Test
	void testAddCurvePointForm() {
		String resultView = curvePointService.addCurvePointForm(model);

		verify(model, times(1)).addAttribute(eq("curvePoint"), any(CurvePoint.class));
		verify(model).addAttribute(eq("curvePoint"), any(CurvePoint.class));

		assertEquals("curvePoint/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		CurvePoint sampleCurvePoint = new CurvePoint();

		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(sampleCurvePoint));

		String resultView = curvePointService.getUpdateForm(1, model);

		verify(curvePointRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("curvePoint"), same(sampleCurvePoint));

		assertEquals("curvePoint/update", resultView);
	}

	@Test
	void testGetUpdateFormCurvePointNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = curvePointService.getUpdateForm(1, model);

		verify(curvePointRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(CurvePoint.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("CurvePoint not found"));

		assertEquals("redirect:/curvePoint/list", resultView);
	}

	@Test
	void testSaveCurvePointList() {
		CurvePointDTO curvePointDTO = new CurvePointDTO();
		curvePointDTO.setCurveId(1);
		curvePointDTO.setTerm(10.0);
		curvePointDTO.setValue(20.0);

		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(1);
		curvePoint.setTerm(10.0);
		curvePoint.setValue(20.0);

		when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = curvePointService.saveCurvePoint(curvePointDTO, redirectAttributes);

		verify(curvePointMapper, times(1)).updateCurvePointFromDto(eq(curvePointDTO), any(CurvePoint.class));
		verify(curvePointRepository, times(1)).save(any(CurvePoint.class));

		assertEquals("CurvePoint successfully added", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/curvePoint/list", resultView);
	}

	@Test
	void testUpdateCurvePointListSuccess() {
		Integer curvePointId = 1;
		CurvePointDTO curvePointDTO = new CurvePointDTO();
		curvePointDTO.setCurveId(1);
		curvePointDTO.setTerm(10.0);
		curvePointDTO.setValue(20.0);

		CurvePoint existingCurvePoint = new CurvePoint();

		when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.of(existingCurvePoint));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = curvePointService.updateCurvePoint(curvePointId, curvePointDTO, redirectAttributes);

		verify(curvePointMapper, times(1)).updateCurvePointFromDto(curvePointDTO, existingCurvePoint);
		verify(curvePointRepository, times(1)).save(existingCurvePoint);

		assertEquals("CurvePoint successfully updated", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/curvePoint/list", resultView);
	}

	@Test
	void testUpdateCurvePointListNotFound() {
		Integer curvePointId = 1;
		CurvePointDTO curvePointDTO = new CurvePointDTO();

		when(curvePointRepository.findById(curvePointId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = curvePointService.updateCurvePoint(curvePointId, curvePointDTO, redirectAttributes);

		verify(curvePointMapper, never()).updateCurvePointFromDto(any(), any());
		verify(curvePointRepository, never()).save(any());

		assertEquals("CurvePoint not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/curvePoint/list", resultView);
	}

	@Test
	void testDeleteCurvePointSuccess() {
		CurvePoint curvePoint = new CurvePoint();

		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = curvePointService.deleteCurvePoint(1, redirectAttributes);

		verify(curvePointRepository, times(1)).deleteById(1);

		assertEquals("CurvePoint successfully deleted", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/curvePoint/list", resultView);
	}

	@Test
	void testDeleteCurvePointNotFound() {
		when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = curvePointService.deleteCurvePoint(1, redirectAttributes);

		verify(curvePointRepository, never()).deleteById(anyInt());

		assertEquals("CurvePoint not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/curvePoint/list", resultView);
	}
}
