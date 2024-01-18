package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.DTO.TradeDTO;

public interface TradeService {

	String getAllTrades(Model model);

	String addTradeForm(Model model);

	String saveTrade(TradeDTO tradeDTO, RedirectAttributes redirectAttributes);

	String getUpdateForm(Integer id, Model model);

	String updateTrade(Integer id, TradeDTO tradeDTO, RedirectAttributes redirectAttributes);

	String deleteTrade(Integer id, RedirectAttributes redirectAttributes);
}
