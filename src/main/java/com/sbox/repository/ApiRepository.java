package com.sbox.repository;

import com.sbox.domain.Api;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Api entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {

}
