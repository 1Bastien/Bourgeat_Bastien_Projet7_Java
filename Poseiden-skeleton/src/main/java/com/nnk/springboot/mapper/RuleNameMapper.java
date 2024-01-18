package com.nnk.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.DTO.RuleNameDTO;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RuleNameMapper {

	RuleNameMapper INSTANCE = Mappers.getMapper(RuleNameMapper.class);

	void updateRuleNameFromDto(RuleNameDTO ruleNameDTO, @MappingTarget RuleName ruleName);
}
