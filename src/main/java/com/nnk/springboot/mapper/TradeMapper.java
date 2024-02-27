package com.nnk.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.DTO.TradeDTO;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradeMapper {

	TradeMapper INSTANCE = Mappers.getMapper(TradeMapper.class);

	void updateTradeFromDto(TradeDTO tradeDTO, @MappingTarget Trade trade);
}
