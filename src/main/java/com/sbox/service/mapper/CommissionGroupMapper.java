package com.sbox.service.mapper;

import com.sbox.domain.*;
import com.sbox.service.dto.CommissionGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommissionGroup and its DTO CommissionGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {OperatorMapper.class})
public interface CommissionGroupMapper extends EntityMapper<CommissionGroupDTO, CommissionGroup> {

    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(source = "operator.name", target = "operatorName")
    CommissionGroupDTO toDto(CommissionGroup commissionGroup);

    @Mapping(source = "operatorId", target = "operator")
    CommissionGroup toEntity(CommissionGroupDTO commissionGroupDTO);

    default CommissionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommissionGroup commissionGroup = new CommissionGroup();
        commissionGroup.setId(id);
        return commissionGroup;
    }
}
