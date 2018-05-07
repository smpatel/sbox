package com.sbox.repository;

import com.sbox.domain.CommissionGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommissionGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionGroupRepository extends JpaRepository<CommissionGroup, Long> {

}
