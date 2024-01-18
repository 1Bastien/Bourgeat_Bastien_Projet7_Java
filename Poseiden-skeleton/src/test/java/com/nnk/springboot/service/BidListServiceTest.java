package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.DTO.BidListDTO;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.impl.BidListServiceImpl;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

	@InjectMocks
	private BidListServiceImpl bidListService;

	@Mock
	private BidListRepository bidListRepository;

	@Mock
	private Model model;

	@Mock
	private BidListMapper bidListMapper;

	@Test
	void testGetAllBidLists() {
		BidList bidList = new BidList();
		List<BidList> bidLists = Arrays.asList(bidList);

		when(bidListRepository.findAll()).thenReturn(bidLists);

		String resultView = bidListService.getAllBidLists(model);

		verify(bidListRepository, times(1)).findAll();
		verify(model, times(1)).addAttribute(eq("bidLists"), anyList());
		verify(model).addAttribute("bidLists", bidLists);

		assertEquals("bidList/list", resultView);
	}

	@Test
	void testAddBidListForm() {
		String resultView = bidListService.addBidListForm(model);

		verify(model, times(1)).addAttribute(eq("bidList"), any(BidList.class));
		verify(model).addAttribute(eq("bidList"), any(BidList.class));

		assertEquals("bidList/add", resultView);
	}

	@Test
	void testGetUpdateForm() {
		BidList sampleBidList = new BidList();

		when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(sampleBidList));

		String resultView = bidListService.getUpdateForm(1, model);

		verify(bidListRepository, times(1)).findById(eq(1));
		verify(model, times(1)).addAttribute(eq("bidList"), same(sampleBidList));

		assertEquals("bidList/update", resultView);
	}

	@Test
	void testGetUpdateFormBidListNotFound() {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

		String resultView = bidListService.getUpdateForm(1, model);

		verify(bidListRepository, times(1)).findById(eq(1));
		verify(model, never()).addAttribute(anyString(), any(BidList.class));
		verify(model, times(1)).addAttribute(eq("error"), eq("BidList not found"));

		assertEquals("redirect:/bidList/list", resultView);
	}

	@Test
	void testSaveBidList() {
		BidListDTO bidListDTO = new BidListDTO();
		bidListDTO.setAccount("account");
		bidListDTO.setType("type");
		bidListDTO.setBidQuantity(10.0);

		BidList bidList = new BidList();
		bidList.setAccount("account");
		bidList.setType("type");
		bidList.setBidQuantity(10.0);

		when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = bidListService.saveBidList(bidListDTO, redirectAttributes);

		verify(bidListMapper, times(1)).updateBidListFromDto(eq(bidListDTO), any(BidList.class));
		verify(bidListRepository, times(1)).save(any(BidList.class));

		assertEquals("BidList successfully added", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/bidList/list", resultView);
	}

	@Test
	void testUpdateBidListSuccess() {
		Integer bidListId = 1;
		BidListDTO bidListDTO = new BidListDTO();
		bidListDTO.setAccount("newAccount");
		bidListDTO.setType("newType");
		bidListDTO.setBidQuantity(20.0);

		BidList existingBidList = new BidList();

		when(bidListRepository.findById(bidListId)).thenReturn(Optional.of(existingBidList));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = bidListService.updateBidList(bidListId, bidListDTO, redirectAttributes);

		verify(bidListMapper, times(1)).updateBidListFromDto(bidListDTO, existingBidList);
		verify(bidListRepository, times(1)).save(existingBidList);

		assertEquals("BidList successfully updated", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/bidList/list", resultView);
	}

	@Test
	void testUpdateBidListNotFound() {
		Integer bidListId = 1;
		BidListDTO bidListDTO = new BidListDTO();

		when(bidListRepository.findById(bidListId)).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = bidListService.updateBidList(bidListId, bidListDTO, redirectAttributes);

		verify(bidListMapper, never()).updateBidListFromDto(any(), any());
		verify(bidListRepository, never()).save(any());

		assertEquals("BidList not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/bidList/list", resultView);
	}

	@Test
	void testDeleteBidSuccess() {
		BidList bidList = new BidList();

		when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = bidListService.deleteBid(1, redirectAttributes);

		verify(bidListRepository, times(1)).deleteById(1);

		assertEquals("BidList successfully deleted", redirectAttributes.getFlashAttributes().get("success"));
		assertEquals("redirect:/bidList/list", resultView);
	}

	@Test
	void testDeleteBidNotFound() {
		when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

		String resultView = bidListService.deleteBid(1, redirectAttributes);

		verify(bidListRepository, never()).deleteById(anyInt());

		assertEquals("BidList not found", redirectAttributes.getFlashAttributes().get("error"));
		assertEquals("redirect:/bidList/list", resultView);
	}
}