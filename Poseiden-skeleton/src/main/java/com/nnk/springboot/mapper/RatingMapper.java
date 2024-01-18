package com.nnk.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.DTO.RatingDTO;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper {

	RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

	void updateRatingFromDto(RatingDTO ratingDTO, @MappingTarget Rating rating);
}
