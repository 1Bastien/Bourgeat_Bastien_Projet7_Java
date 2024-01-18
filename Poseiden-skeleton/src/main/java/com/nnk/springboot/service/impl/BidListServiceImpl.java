package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.DTO.BidListDTO;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

@Service
public class BidListServiceImpl implements BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	private BidListMapper bidListMapper;

	@Transactional(readOnly = true)
	public String getAllBidLists(Model model) {
		try {
			List<BidList> bidLists = bidListRepository.findAll();
			model.addAttribute("bidLists", bidLists);
		} catch (Exception e) {
			throw new RuntimeException("BidList not found");
		}
		return "bidList/list";
	}

	public String addBidListForm(Model model) {
		try {
			BidList bidList = new BidList();
			model.addAttribute("bidList", bidList);
		} catch (Exception e) {
			throw new RuntimeException("Error getting bidList form");
		}

		return "bidList/add";
	}

	@Transactional(readOnly = true)
	public String getUpdateForm(Integer id, Model model) {
		try {
			Optional<BidList> bidList = bidListRepository.findById(id);

			if (bidList.isPresent()) {
				model.addAttribute("bidList", bidList.get());
			} else {
				model.addAttribute("error", "BidList not found");
				return "redirect:/bidList/list";
			}
		} catch (Exception e) {
			throw new RuntimeException("Error getting bidList form");
		}

		return "bidList/update";
	}

	@Transactional
	public String saveBidList(BidListDTO bidListDTO, RedirectAttributes redirectAttributes) {
		try {
			BidList bidList = new BidList();

			bidListMapper.updateBidListFromDto(bidListDTO, bidList);

			bidListRepository.save(bidList);
			redirectAttributes.addFlashAttribute("success", "BidList successfully added");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "BidList not added");
		}

		return "redirect:/bidList/list";
	}

	@Transactional
	public String updateBidList(Integer id, BidListDTO bidList, RedirectAttributes redirectAttributes) {
		try {
			Optional<BidList> bidListToUpdate = bidListRepository.findById(id);
			if (bidListToUpdate.isPresent()) {

				BidList oldBidList = bidListToUpdate.get();

				bidListMapper.updateBidListFromDto(bidList, oldBidList);

				bidListRepository.save(oldBidList);
				redirectAttributes.addFlashAttribute("success", "BidList successfully updated");

			} else {
				redirectAttributes.addFlashAttribute("error", "BidList not found");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "BidList not updated");
		}

		return "redirect:/bidList/list";
	}

	@Transactional
	public String deleteBid(Integer id, RedirectAttributes redirectAttributes) {
		try {
			Optional<BidList> bidList = bidListRepository.findById(id);
			if (bidList.isPresent()) {
				bidListRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("success", "BidList successfully deleted");
			} else {
				redirectAttributes.addFlashAttribute("error", "BidList not found");
			}

		} catch (Exception e) {
			throw new RuntimeException("Error deleting bidList");
		}

		return "redirect:/bidList/list";
	}
}
