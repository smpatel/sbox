package com.sbox.service;

import com.sbox.domain.Authority;
import com.sbox.domain.User;
import com.sbox.domain.UserAddon;
import com.sbox.repository.UserAddonRepository;
import com.sbox.repository.UserRepository;
import com.sbox.security.SecurityUtils;
import com.sbox.service.dto.UserAddonDTO;
import com.sbox.service.dto.UserDTO;
import com.sbox.service.mapper.UserAddonMapper;
import com.sbox.web.rest.errors.BadRequestAlertException;
import com.sbox.web.rest.errors.EmailAlreadyUsedException;
import com.sbox.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Service Implementation for managing UserAddon.
 */
@Service
@Transactional
public class UserAddonService {

    private final Logger log = LoggerFactory.getLogger(UserAddonService.class);

    private final UserAddonRepository userAddonRepository;

    private final UserRepository userRepository;

    private final UserAddonMapper userAddonMapper;

    private final UserService userService;

    private final MailService mailService;

    public UserAddonService(UserRepository userRepository,UserAddonRepository userAddonRepository, UserAddonMapper userAddonMapper, UserService userService, MailService mailService) {
        this.userRepository = userRepository;
        this.userAddonRepository = userAddonRepository;
        this.userAddonMapper = userAddonMapper;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * Save a userAddon.
     *
     * @param userAddonDTO the entity to save
     * @return the persisted entity
     */
    public UserAddonDTO save(UserAddonDTO userAddonDTO) {
        log.debug("Request to save UserAddon : {}", userAddonDTO);

        if (userAddonDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userAddonDTO.getUsername().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userAddonDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {

            // USER CREATE

            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(userAddonDTO.getUsername());
            userDTO.setFirstName(userAddonDTO.getName());
            userDTO.setEmail(userAddonDTO.getEmail());
            userDTO.setActivated(userAddonDTO.isActivated());
            User newUser = userService.createUser(userDTO);
            userAddonDTO.setPassword("xxx");
            userAddonDTO.setUserId(newUser.getId());

            final UserAddon userAddon = userAddonMapper.toEntity(userAddonDTO);
            SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> userAddon.setParentUserId(user.getId()));

                    // USER ADDITIONAL INFORMATION
            UserAddon createdUserAddon = userAddonRepository.save(userAddon);
            mailService.sendCreationEmail(newUser);
            return userAddonMapper.toDto(createdUserAddon);
        }
    }

    /**
     * Save a userAddon.
     *
     * @param userAddonDTO the entity to save
     * @return the persisted entity
     */
    public UserAddonDTO update(UserAddonDTO userAddonDTO) {
        log.debug("Request to save UserAddon : {}", userAddonDTO);

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userAddonDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userAddonDTO.getUserId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userAddonDTO.getUserLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userAddonDTO.getUserId()))) {
            throw new LoginAlreadyUsedException();
        }

        // USER UPDATE
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userAddonDTO.getUserId());
        userDTO.setLogin(userAddonDTO.getUsername());
        userDTO.setFirstName(userAddonDTO.getName());
        userDTO.setEmail(userAddonDTO.getEmail());
        userDTO.setActivated(userAddonDTO.isActivated());
        Set<String> managedAuthorities = new HashSet<>();
        managedAuthorities.add(userAddonDTO.getSecurityPassword());
        userDTO.setAuthorities(managedAuthorities);
        userService.updateUser(userDTO);

        final UserAddon userAddon = userAddonMapper.toEntity(userAddonDTO);
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> userAddon.setParentUserId(user.getId()));

        System.out.println("\n PARENT ID--"+userAddon.getParentUserId());

        UserAddon returnUserAddon = userAddonRepository.save(userAddon);
        return userAddonMapper.toDto(returnUserAddon);

    }

    /**
     * Get all the userAddons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserAddonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAddons");
        return userAddonRepository.findAll(pageable)
            .map(userAddonMapper::toDto);
    }

    /**
     * Get one userAddon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserAddonDTO findOne(Long id) {
        log.debug("Request to get UserAddon : {}", id);
        UserAddon userAddon = userAddonRepository.findOne(id);
        return userAddonMapper.toDto(userAddon);
    }

    /**
     * Delete the userAddon by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAddon : {}", id);
        userAddonRepository.delete(id);
    }
}
