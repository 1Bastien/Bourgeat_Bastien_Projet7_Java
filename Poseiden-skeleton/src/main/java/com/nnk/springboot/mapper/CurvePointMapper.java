package com.nnk.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.factory.Mappers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.DTO.CurvePointDTO;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurvePointMapper {

	CurvePointMapper INSTANCE = Mappers.getMapper(CurvePointMapper.class);

	void updateCurvePointFromDto(CurvePointDTO curvePointDTO, @MappingTarget CurvePoint curvePoint);
}
