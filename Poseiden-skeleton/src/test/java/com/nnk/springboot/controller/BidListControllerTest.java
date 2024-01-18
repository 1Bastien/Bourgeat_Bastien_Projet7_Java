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

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.DTO.BidListDTO;
import com.nnk.springboot.service.BidListService;

@WebMvcTest(BidListController.class)
class BidListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BidListService bidListService;

	@Test
	@WithMockUser(username = "john@test.com")
	void testHome() throws Exception {
		BidList bidList = new BidList();
		List<BidList> bidLists = new ArrayList<>();
		bidLists.add(bidList);

		when(bidListService.getAllBidLists(any(Model.class))).thenReturn("bidList/list");

		mockMvc.perform(get("/bidList/list").flashAttr("bidLists", bidLists)).andExpect(status().isOk())
				.andExpect(view().name("bidList/list")).andExpect(model().attributeExists("bidLists"));

		verify(bidListService, times(1)).getAllBidLists(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testaddBidForm() throws Exception {
		BidList bidList = new BidList();

		when(bidListService.addBidListForm(any(Model.class))).thenReturn("bidList/add");

		mockMvc.perform(get("/bidList/add").flashAttr("bidList", bidList)).andExpect(status().isOk())
				.andExpect(view().name("bidList/add")).andExpect(model().attributeExists("bidList"));

		verify(bidListService, times(1)).addBidListForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidate() throws Exception {
		BidListDTO bidList = new BidListDTO();
		bidList.setAccount("Account");
		bidList.setType("Type");
		bidList.setBidQuantity(10.0);

		when(bidListService.saveBidList(any(BidListDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/bidList/list");

		mockMvc.perform(post("/bidList/validate").with(csrf()).flashAttr("bidList", bidList))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/bidList/list"));

		verify(bidListService, times(1)).saveBidList(eq(bidList), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidateWithErrors() throws Exception {
		BidListDTO bidList = new BidListDTO();

		mockMvc.perform(post("/bidList/validate").with(csrf()).flashAttr("bidList", bidList)).andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));

		verify(bidListService, never()).saveBidList(eq(bidList), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testShowUpdateForm() throws Exception {
		BidList bidList = new BidList();

		when(bidListService.getUpdateForm(eq(1), any(Model.class))).thenReturn("bidList/update");

		mockMvc.perform(get("/bidList/update/1").flashAttr("bidList", bidList)).andExpect(status().isOk())
				.andExpect(view().name("bidList/update")).andExpect(model().attributeExists("bidList"));

		verify(bidListService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateBid() throws Exception {
		BidListDTO bidList = new BidListDTO();
		bidList.setAccount("Account");
		bidList.setType("Type");
		bidList.setBidQuantity(10.0);

		when(bidListService.updateBidList(eq(1), any(BidListDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/bidList/list");

		mockMvc.perform(post("/bidList/update/1").with(csrf()).flashAttr("bidList", bidList))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/bidList/list"));

		verify(bidListService, times(1)).updateBidList(eq(1), any(BidListDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateBidWithErrors() throws Exception {
		BidListDTO bidList = new BidListDTO();

		mockMvc.perform(post("/bidList/update/1").with(csrf()).flashAttr("bidList", bidList)).andExpect(status().isOk())
				.andExpect(view().name("bidList/update"));

		verify(bidListService, never()).updateBidList(eq(1), any(BidListDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testDeleteBid() throws Exception {
		when(bidListService.deleteBid(eq(1), any(RedirectAttributes.class))).thenReturn("redirect:/bidList/list");

		mockMvc.perform(get("/bidList/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/bidList/list"));

		verify(bidListService, times(1)).deleteBid(eq(1), any(RedirectAttributes.class));
	}
}
