package com.sbox.service.mapper;

import com.sbox.domain.*;
import com.sbox.service.dto.UserAddonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAddon and its DTO UserAddonDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CommissionGroupMapper.class})
public interface UserAddonMapper extends EntityMapper<UserAddonDTO, UserAddon> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.activated", target = "activated")
    @Mapping(source = "commissionGroup.id", target = "commissionGroupId")
    @Mapping(source = "commissionGroup.name", target = "commissionGroupName")
    UserAddonDTO toDto(UserAddon userAddon);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "commissionGroupId", target = "commissionGroup")
    UserAddon toEntity(UserAddonDTO userAddonDTO);

    default UserAddon fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAddon userAddon = new UserAddon();
        userAddon.setId(id);
        return userAddon;
    }
}
