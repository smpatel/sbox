package com.sbox.service.mapper;

import com.sbox.domain.*;
import com.sbox.service.dto.OperatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operator and its DTO OperatorDTO.
 */
@Mapper(componentModel = "spring", uses = {ApiMapper.class})
public interface OperatorMapper extends EntityMapper<OperatorDTO, Operator> {

    @Mapping(source = "firstApi.id", target = "firstApiId")
    @Mapping(source = "firstApi.name", target = "firstApiName")
    @Mapping(source = "secondApi.id", target = "secondApiId")
    @Mapping(source = "secondApi.name", target = "secondApiName")
    @Mapping(source = "thirdApi.id", target = "thirdApiId")
    @Mapping(source = "thirdApi.name", target = "thirdApiName")
    OperatorDTO toDto(Operator operator);

    @Mapping(source = "firstApiId", target = "firstApi")
    @Mapping(source = "secondApiId", target = "secondApi")
    @Mapping(source = "thirdApiId", target = "thirdApi")
    Operator toEntity(OperatorDTO operatorDTO);

    default Operator fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operator operator = new Operator();
        operator.setId(id);
        return operator;
    }
}
