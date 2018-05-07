package com.sbox.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sbox.domain.Api;

import com.sbox.repository.ApiRepository;
import com.sbox.web.rest.errors.BadRequestAlertException;
import com.sbox.web.rest.util.HeaderUtil;
import com.sbox.service.dto.ApiDTO;
import com.sbox.service.mapper.ApiMapper;
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
 * REST controller for managing Api.
 */
@RestController
@RequestMapping("/api")
public class ApiResource {

    private final Logger log = LoggerFactory.getLogger(ApiResource.class);

    private static final String ENTITY_NAME = "api";

    private final ApiRepository apiRepository;

    private final ApiMapper apiMapper;

    public ApiResource(ApiRepository apiRepository, ApiMapper apiMapper) {
        this.apiRepository = apiRepository;
        this.apiMapper = apiMapper;
    }

    /**
     * POST  /apis : Create a new api.
     *
     * @param apiDTO the apiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiDTO, or with status 400 (Bad Request) if the api has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apis")
    @Timed
    public ResponseEntity<ApiDTO> createApi(@Valid @RequestBody ApiDTO apiDTO) throws URISyntaxException {
        log.debug("REST request to save Api : {}", apiDTO);
        if (apiDTO.getId() != null) {
            throw new BadRequestAlertException("A new api cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Api api = apiMapper.toEntity(apiDTO);
        api = apiRepository.save(api);
        ApiDTO result = apiMapper.toDto(api);
        return ResponseEntity.created(new URI("/api/apis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apis : Updates an existing api.
     *
     * @param apiDTO the apiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiDTO,
     * or with status 400 (Bad Request) if the apiDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apis")
    @Timed
    public ResponseEntity<ApiDTO> updateApi(@Valid @RequestBody ApiDTO apiDTO) throws URISyntaxException {
        log.debug("REST request to update Api : {}", apiDTO);
        if (apiDTO.getId() == null) {
            return createApi(apiDTO);
        }
        Api api = apiMapper.toEntity(apiDTO);
        api = apiRepository.save(api);
        ApiDTO result = apiMapper.toDto(api);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apis : get all the apis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apis in body
     */
    @GetMapping("/apis")
    @Timed
    public List<ApiDTO> getAllApis() {
        log.debug("REST request to get all Apis");
        List<Api> apis = apiRepository.findAll();
        return apiMapper.toDto(apis);
        }

    /**
     * GET  /apis/:id : get the "id" api.
     *
     * @param id the id of the apiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/apis/{id}")
    @Timed
    public ResponseEntity<ApiDTO> getApi(@PathVariable Long id) {
        log.debug("REST request to get Api : {}", id);
        Api api = apiRepository.findOne(id);
        ApiDTO apiDTO = apiMapper.toDto(api);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apiDTO));
    }

    /**
     * DELETE  /apis/:id : delete the "id" api.
     *
     * @param id the id of the apiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apis/{id}")
    @Timed
    public ResponseEntity<Void> deleteApi(@PathVariable Long id) {
        log.debug("REST request to delete Api : {}", id);
        apiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
