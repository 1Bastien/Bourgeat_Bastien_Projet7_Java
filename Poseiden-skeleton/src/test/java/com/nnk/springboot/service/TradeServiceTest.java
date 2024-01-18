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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.DTO.TradeDTO;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.impl.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

	@InjectMocks
	private TradeServiceImpl tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private Model model;

	@Mock
	private TradeMapper tradeMapper;

	@Test
	void testGetAllTrades() {
		Trade trade = new Trade();
		List<Trade> trades = Arrays.asList(trade);

		when(tradeRepository.findAll()).thenReturn(trades);

		String resultView = tradeService.getAllTrades(model);

		verify(tradeRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("trades"), anyList());
		verify(model).addAttribute("trades", trades);

		assertEquals("trade/list", resultView);
	}

	@Test
	void testAddTradeForm() {
		String resultView = tradeService.addTradeForm(model);

		verify(model, times(1)).addAttribute(eq("trade"), any(Trade.class));
		verify(model).addAttribute(eq("trade"), any(Trade.class));

		assertEquals("trade/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		Trade trade = new Trade();

		when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

		String resultView = tradeService.getUpdateForm(1, model);

		verify(tradeRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("trade"), same(trade));

		assertEquals("trade/update", resultView);
	}

	@Test
	void testGetUpdateFormTradeNotFound() {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = tradeService.getUpdateForm(1, model);

		verify(tradeRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(Trade.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("Trade not found"));

		assertEquals("redirect:/trade/list", resultView);
	}

	@Test
	void testSaveTradeList() {
		TradeDTO tradeDTO = new TradeDTO();

		Trade trade = new Trade();

		when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = tradeService.saveTrade(tradeDTO, redirectAttributes);

		verify(tradeMapper, times(1)).updateTradeFromDto(eq(tradeDTO), any(Trade.class));
		verify(tradeRepository, times(1)).save(any(Trade.class));

		assertEquals("Trade saved successfully", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/trade/list", resultView);
	}

	@Test
	void testUpdateTradeSuccess() {
		Integer tradeId = 1;
		TradeDTO tradeDTO = new TradeDTO();

		Trade existingTrade = new Trade();

		when(tradeRepository.findById(tradeId)).thenReturn(Optional.of(existingTrade));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = tradeService.updateTrade(tradeId, tradeDTO, redirectAttributes);

		verify(tradeMapper, times(1)).updateTradeFromDto(tradeDTO, existingTrade);
		verify(tradeRepository, times(1)).save(existingTrade);

		assertEquals("Trade updated successfully", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/trade/list", resultView);
	}

	@Test
	void testUpdateTradeNotFound() {
		Integer tradeId = 1;
		TradeDTO tradeDTO = new TradeDTO();

		when(tradeRepository.findById(tradeId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = tradeService.updateTrade(tradeId, tradeDTO, redirectAttributes);

		verify(tradeMapper, never()).updateTradeFromDto(any(), any());
		verify(tradeRepository, never()).save(any());

		assertEquals("Trade not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/trade/list", resultView);
	}

	@Test
	void testDeleteTradeSuccess() {
		Trade bidList = new Trade();

		when(tradeRepository.findById(1)).thenReturn(Optional.of(bidList));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = tradeService.deleteTrade(1, redirectAttributes);

		verify(tradeRepository, times(1)).deleteById(1);

		assertEquals("Trade deleted successfully", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/trade/list", resultView);
	}

	@Test
	void testDeleteTradeNotFound() {
		when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = tradeService.deleteTrade(1, redirectAttributes);

		verify(tradeRepository, never()).deleteById(anyInt());

		assertEquals("Trade not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/trade/list", resultView);
	}
}