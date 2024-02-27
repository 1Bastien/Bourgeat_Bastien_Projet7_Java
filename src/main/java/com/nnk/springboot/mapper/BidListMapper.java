package com.nnk.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.DTO.BidListDTO;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidListMapper {

	BidListMapper INSTANCE = Mappers.getMapper(BidListMapper.class);

	void updateBidListFromDto(BidListDTO bidListDTO, @MappingTarget BidList bidList);
}
