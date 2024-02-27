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

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.DTO.TradeDTO;
import com.nnk.springboot.service.TradeService;

@WebMvcTest(TradeController.class)
class TradeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TradeService tradeService;

	@Test
	@WithMockUser(username = "john@test.com")
	void testHome() throws Exception {
		Trade trade = new Trade();
		List<Trade> trades = new ArrayList<>();
		trades.add(trade);

		when(tradeService.getAllTrades(any(Model.class))).thenReturn("trade/list");

		mockMvc.perform(get("/trade/list").flashAttr("trades", trades)).andExpect(status().isOk())
				.andExpect(view().name("trade/list")).andExpect(model().attributeExists("trades"));

		verify(tradeService, times(1)).getAllTrades(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testaddTradeForm() throws Exception {
		Trade trade = new Trade();

		when(tradeService.addTradeForm(any(Model.class))).thenReturn("trade/add");

		mockMvc.perform(get("/trade/add").flashAttr("trade", trade)).andExpect(status().isOk())
				.andExpect(view().name("trade/add")).andExpect(model().attributeExists("trade"));

		verify(tradeService, times(1)).addTradeForm(any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidate() throws Exception {
		TradeDTO trade = new TradeDTO();
		trade.setAccount("Account");
		trade.setType("Type");
		trade.setBuyQuantity(10.0);

		when(tradeService.saveTrade(any(TradeDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/trade/list");

		mockMvc.perform(post("/trade/validate").with(csrf()).flashAttr("trade", trade))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/trade/list"));

		verify(tradeService, times(1)).saveTrade(eq(trade), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testValidateWithErrors() throws Exception {
		TradeDTO trade = new TradeDTO();
		trade.setAccount("Accountxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/trade/validate").with(csrf()).flashAttr("trade", trade)).andExpect(status().isOk())
				.andExpect(view().name("trade/add"));

		verify(tradeService, never()).saveTrade(eq(trade), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testShowUpdateForm() throws Exception {
		Trade trade = new Trade();

		when(tradeService.getUpdateForm(eq(1), any(Model.class))).thenReturn("trade/update");

		mockMvc.perform(get("/trade/update/1").flashAttr("trade", trade)).andExpect(status().isOk())
				.andExpect(view().name("trade/update")).andExpect(model().attributeExists("trade"));

		verify(tradeService, times(1)).getUpdateForm(eq(1), any(Model.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateTrade() throws Exception {
		TradeDTO trade = new TradeDTO();
		trade.setAccount("Account");
		trade.setType("Type");
		trade.setBuyQuantity(10.0);

		when(tradeService.updateTrade(eq(1), any(TradeDTO.class), any(RedirectAttributes.class)))
				.thenReturn("redirect:/trade/list");

		mockMvc.perform(post("/trade/update/1").with(csrf()).flashAttr("trade", trade))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/trade/list"));

		verify(tradeService, times(1)).updateTrade(eq(1), any(TradeDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testUpdateTradeWithErrors() throws Exception {
		TradeDTO trade = new TradeDTO();
		trade.setAccount("Accountxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		mockMvc.perform(post("/trade/update/1").with(csrf()).flashAttr("trade", trade)).andExpect(status().isOk())
				.andExpect(view().name("trade/update"));

		verify(tradeService, never()).updateTrade(eq(1), any(TradeDTO.class), any(RedirectAttributes.class));
	}

	@Test
	@WithMockUser(username = "john@test.com")
	void testDeleteTrade() throws Exception {
		when(tradeService.deleteTrade(eq(1), any(RedirectAttributes.class))).thenReturn("redirect:/trade/list");

		mockMvc.perform(get("/trade/delete/1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/trade/list"));

		verify(tradeService, times(1)).deleteTrade(eq(1), any(RedirectAttributes.class));
	}
}
