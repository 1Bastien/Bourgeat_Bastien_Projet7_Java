package com.nnk.springboot.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.DTO.BidListDTO;

public interface BidListService {

	String getAllBidLists(Model model);

	String addBidListForm(Model model);

	String getUpdateForm(Integer id, Model model);

	String saveBidList(BidListDTO bidListDTO, RedirectAttributes redirectAttributes);

	String updateBidList(Integer id, BidListDTO bidListDTO, RedirectAttributes redirectAttributes);

	String deleteBid(Integer id, RedirectAttributes redirectAttributes);
}
