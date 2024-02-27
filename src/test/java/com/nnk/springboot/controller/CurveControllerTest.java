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

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.DTO.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@WebMvcTest(CurveController.class)
class CurveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurvePointService curvePointService;

	@Test
	@WithMockUser(username = "john@test.com")
	void testHome() throws Exception {
		CurvePoint curvePoint = new CurvePoint();
		List<CurvePoint> curvePoints = new ArrayList<>();
		curvePoints.add(curvePoint);

		when(curvePointService.getAllCurvePoints(any(Model.class))).thenReturn("curvePoint/list");

		mockMvc.perform(get("/curvePoint/list").flashAttr("curvePoints", curvePoints)).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list")).andExpect(model().attributeExists("curvePoints"));

		verify(curvePointService, times(1)).getAllCurvePoints(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testaddCurvePointForm() throws Exception {
		CurvePoint curvePoint = new CurvePoint();

		when(curvePointService.addCurvePointForm(any(Model.class))).thenReturn("curvePoint/add");

		mockMvc.perform(get("/curvePoint/add").flashAttr("curvePoint", curvePoint)).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add")).andExpect(model().attributeExists("curvePoint"));

		verify(curvePointService, times(1)).addCurvePointForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidate() throws Exception {
		CurvePointDTO curvePoint = new CurvePointDTO();
		curvePoint.setCurveId(1);
		curvePoint.setTerm(10.0);
		curvePoint.setValue(10.0);

		when(curvePointService.saveCurvePoint(any(CurvePointDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/curvePoint/list");

		mockMvc.perform(post("/curvePoint/validate").with(csrf()).flashAttr("curvePoint", curvePoint))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/curvePoint/list"));

		verify(curvePointService, times(1)).saveCurvePoint(eq(curvePoint), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidateWithErrors() throws Exception {
		CurvePointDTO curvePoint = new CurvePointDTO();

		mockMvc.perform(post("/curvePoint/validate").with(csrf()).flashAttr("curvePoint", curvePoint))
				.andExpect(status().isOk()).andExpect(view().name("curvePoint/add"));

		verify(curvePointService, never()).saveCurvePoint(eq(curvePoint), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testShowUpdateForm() throws Exception {
		CurvePoint curvePoint = new CurvePoint();

		when(curvePointService.getUpdateForm(eq(1), any(Model.class))).thenReturn("curvePoint/update");

		mockMvc.perform(get("/curvePoint/update/1").flashAttr("curvePoint", curvePoint)).andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update")).andExpect(model().attributeExists("curvePoint"));

		verify(curvePointService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateCurvePoint() throws Exception {
		CurvePointDTO curvePoint = new CurvePointDTO();
		curvePoint.setCurveId(1);
		curvePoint.setTerm(10.0);
		curvePoint.setValue(10.0);

		when(curvePointService.updateCurvePoint(eq(1), any(CurvePointDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/curvePoint/list");

		mockMvc.perform(post("/curvePoint/update/1").with(csrf()).flashAttr("curvePoint", curvePoint))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/curvePoint/list"));

		verify(curvePointService, times(1)).updateCurvePoint(eq(1), any(CurvePointDTO.class),
				any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateCurvePointWithErrors() throws Exception {
		CurvePointDTO curvePoint = new CurvePointDTO();

		mockMvc.perform(post("/curvePoint/update/1").with(csrf()).flashAttr("curvePoint", curvePoint))
				.andExpect(status().isOk()).andExpect(view().name("curvePoint/update"));

		verify(curvePointService, never()).updateCurvePoint(eq(1), any(CurvePointDTO.class),
				any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testDeleteCurvePoint() throws Exception {
		when(curvePointService.deleteCurvePoint(eq(1), any(RedirectAttributes.class)))
				.thenReturn("redirect:/curvePoint/list");

		mockMvc.perform(get("/curvePoint/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/curvePoint/list"));

		verify(curvePointService, times(1)).deleteCurvePoint(eq(1), any(RedirectAttributes.class));
	}
}
