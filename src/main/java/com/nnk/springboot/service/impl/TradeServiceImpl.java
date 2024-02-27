package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.DTO.TradeDTO;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeMapper tradeMapper;

	@Transactional(readOnly = true)
	public String getAllTrades(Model model) {
		try {
			List<Trade> trades = tradeRepository.findAll();
			model.addAttribute("trades", trades);
		} catch (Exception e) {
			throw new RuntimeException("Error while getting all trades", e);
		}
		return "trade/list";
	}

	public String addTradeForm(Model model) {
		try {
			Trade trade = new Trade();
			model.addAttribute("trade", trade);
		} catch (Exception e) {
			throw new RuntimeException("Error while adding trade form", e);
		}

		return "trade/add";
	}

	@Transactional
	public String saveTrade(TradeDTO tradeDTO, RedirectAttributes redirectAttributes) {
		try {
			Trade trade = new Trade();

			tradeMapper.updateTradeFromDto(tradeDTO, trade);

			tradeRepository.save(trade);
			redirectAttributes.addFlashAttribute("success", "Trade saved successfully");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error while saving trade");
		}
		return "redirect:/trade/list";
	}

	@Transactional(readOnly = true)
	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<Trade> optionalTrade = tradeRepository.findById(id);
			if (optionalTrade.isPresent()) {
				model.addAttribute("trade", optionalTrade.get());
			} else {
				model.addAttribute("error", "Trade not found");
				return "redirect:/trade/list";
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while getting update form", e);
		}
		return "trade/update";
	}

	@Transactional
	public String updateTrade(Integer id, TradeDTO trade, RedirectAttributes redirectAttributes) {
		try {
			Optional<Trade> optionalTrade = tradeRepository.findById(id);
			if (optionalTrade.isPresent()) {

				Trade tradeToUpdate = optionalTrade.get();

				tradeMapper.updateTradeFromDto(trade, tradeToUpdate);

				tradeRepository.save(tradeToUpdate);
				redirectAttributes.addFlashAttribute("success", "Trade updated successfully");
			} else {
				redirectAttributes.addFlashAttribute("error", "Trade not found");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while updating trade", e);
		}
		return "redirect:/trade/list";
	}

	@Transactional
	public String deleteTrade(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<Trade> optionalTrade = tradeRepository.findById(id);
			if (optionalTrade.isPresent()) {
				tradeRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "Trade deleted successfully");
			} else {
				redirectAttributes.addFlashAttribute("error", "Trade not found");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while deleting trade", e);
		}
		return "redirect:/trade/list";
	}
}
