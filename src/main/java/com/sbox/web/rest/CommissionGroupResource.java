package com.sbox.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sbox.domain.CommissionGroup;

import com.sbox.repository.CommissionGroupRepository;
import com.sbox.web.rest.errors.BadRequestAlertException;
import com.sbox.web.rest.util.HeaderUtil;
import com.sbox.service.dto.CommissionGroupDTO;
import com.sbox.service.mapper.CommissionGroupMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommissionGroup.
 */
@RestController
@RequestMapping("/api")
public class CommissionGroupResource {

    private final Logger log = LoggerFactory.getLogger(CommissionGroupResource.class);

    private static final String ENTITY_NAME = "commissionGroup";

    private final CommissionGroupRepository commissionGroupRepository;

    private final CommissionGroupMapper commissionGroupMapper;

    public CommissionGroupResource(CommissionGroupRepository commissionGroupRepository, CommissionGroupMapper commissionGroupMapper) {
        this.commissionGroupRepository = commissionGroupRepository;
        this.commissionGroupMapper = commissionGroupMapper;
    }

    /**
     * POST  /commission-groups : Create a new commissionGroup.
     *
     * @param commissionGroupDTO the commissionGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commissionGroupDTO, or with status 400 (Bad Request) if the commissionGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commission-groups")
    @Timed
    public ResponseEntity<CommissionGroupDTO> createCommissionGroup(@Valid @RequestBody CommissionGroupDTO commissionGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CommissionGroup : {}", commissionGroupDTO);
        if (commissionGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new commissionGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommissionGroup commissionGroup = commissionGroupMapper.toEntity(commissionGroupDTO);
        commissionGroup = commissionGroupRepository.save(commissionGroup);
        CommissionGroupDTO result = commissionGroupMapper.toDto(commissionGroup);
        return ResponseEntity.created(new URI("/api/commission-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commission-groups : Updates an existing commissionGroup.
     *
     * @param commissionGroupDTO the commissionGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commissionGroupDTO,
     * or with status 400 (Bad Request) if the commissionGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the commissionGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commission-groups")
    @Timed
    public ResponseEntity<CommissionGroupDTO> updateCommissionGroup(@Valid @RequestBody CommissionGroupDTO commissionGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CommissionGroup : {}", commissionGroupDTO);
        if (commissionGroupDTO.getId() == null) {
            return createCommissionGroup(commissionGroupDTO);
        }
        CommissionGroup commissionGroup = commissionGroupMapper.toEntity(commissionGroupDTO);
        commissionGroup = commissionGroupRepository.save(commissionGroup);
        CommissionGroupDTO result = commissionGroupMapper.toDto(commissionGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commissionGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commission-groups : get all the commissionGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commissionGroups in body
     */
    @GetMapping("/commission-groups")
    @Timed
    public List<CommissionGroupDTO> getAllCommissionGroups() {
        log.debug("REST request to get all CommissionGroups");
        List<CommissionGroup> commissionGroups = commissionGroupRepository.findAll();
        return commissionGroupMapper.toDto(commissionGroups);
        }

    /**
     * GET  /commission-groups/:id : get the "id" commissionGroup.
     *
     * @param id the id of the commissionGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commissionGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/commission-groups/{id}")
    @Timed
    public ResponseEntity<CommissionGroupDTO> getCommissionGroup(@PathVariable Long id) {
        log.debug("REST request to get CommissionGroup : {}", id);
        CommissionGroup commissionGroup = commissionGroupRepository.findOne(id);
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commissionGroupDTO));
    }

    /**
     * DELETE  /commission-groups/:id : delete the "id" commissionGroup.
     *
     * @param id the id of the commissionGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commission-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommissionGroup(@PathVariable Long id) {
        log.debug("REST request to delete CommissionGroup : {}", id);
        commissionGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
