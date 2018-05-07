package com.sbox.service.mapper;

import com.sbox.domain.*;
import com.sbox.service.dto.ApiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Api and its DTO ApiDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiMapper extends EntityMapper<ApiDTO, Api> {



    default Api fromId(Long id) {
        if (id == null) {
            return null;
        }
        Api api = new Api();
        api.setId(id);
        return api;
    }
}
