package com.xedflix.video.service;

import com.amazonaws.services.ec2.model.ResponseError;
import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.api.UserResourceAccessPermissionResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserResourceAccessPermission;
import com.xedflix.video.domain.Livestream;
import com.xedflix.video.repository.LivestreamRepository;
import com.xedflix.video.security.UserRole;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import com.xedflix.video.service.exceptions.ResponseErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.xml.ws.Response;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Livestream.
 */
@Service
@Transactional
public class LivestreamService {

    private static final String ENTITY_NAME = "Livestream";
    private static final String ACTION_NOT_SUPPORTED_MESSAGE =
        "You do not have enough permission to create a live stream";

    private final Logger log = LoggerFactory.getLogger(LivestreamService.class);

    private final LivestreamRepository livestreamRepository;

    @Autowired
    private RoleResourceApiClient roleResourceApiClient;

    @Autowired
    private UserResourceAccessPermissionResourceApiClient userResourceAccessPermissionResourceApiClient;

    public LivestreamService(LivestreamRepository livestreamRepository) {
        this.livestreamRepository = livestreamRepository;
    }

    /**
     * Save a livestream.
     *
     * @param livestream the entity to create
     * @return the persisted entity
     */
    @Transactional(rollbackFor = Exception.class)
    public Livestream create(Livestream livestream) throws ActionNotSupportedException {
        log.debug("Request to create Livestream : {}", livestream);

        livestream.setCreatedAt(ZonedDateTime.now(ZoneId.of("UTC")));

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(ENTITY_NAME);
        if(actionPermissionForRoleResponseEntity.getStatusCode().isError()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole == null) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        if(!actionPermissionForRole.isCanCreate()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        return livestreamRepository.save(livestream);
    }

    /**
     *
     * Update a livestream.
     *
     * @param livestream the live stream to persist
     * @return the persisted entity
     * @throws ActionNotSupportedException If the action is not supported
     */
    @Transactional(rollbackFor = Exception.class)
    public Livestream update(Livestream livestream) throws ActionNotSupportedException, ResourceNotFoundException, InstantiationException, IllegalAccessException {
        log.debug("Request to update Livestream: {}", livestream);

        livestream.setUpdatedAt(ZonedDateTime.now(ZoneId.of("UTC")));

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(ENTITY_NAME);
        if(actionPermissionForRoleResponseEntity.getStatusCode().isError()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole == null) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        if(!actionPermissionForRole.isCanUpdate()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        Livestream existingLivestream = livestreamRepository.findById(livestream.getId()).orElseThrow(ResourceNotFoundException::new);
        Livestream mergedLivestream = Livestream.merge(livestream, existingLivestream);

        return livestreamRepository.save(mergedLivestream);
    }

    /**
     * Get all the livestreams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Livestream> findAll(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
        log.debug("Request to get all Livestreams");

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(ENTITY_NAME);
        if(actionPermissionForRoleResponseEntity.getStatusCode().isError()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole == null) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        if(!actionPermissionForRole.isCanRead()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        Page<Livestream> livestreamPage = Page.empty(pageable);
        switch (userRole) {
            case XEDFLIX_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findAll(pageable);
                break;
            case ORG_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationId(organizationId, pageable);
                break;
            case ORG_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationId(organizationId, pageable);
                break;
            case ORG_USER:
                ResponseEntity<List<UserResourceAccessPermission>> userResourceAccessPermissionResponseEntity =
                    userResourceAccessPermissionResourceApiClient.findResourcesUsingGET(
                        ENTITY_NAME,
                        pageable.getOffset(),
                        null,
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.isPaged(),
                        pageable.getPageSize(),
                        null,
                        false,
                        false,
                        pageable.isUnpaged()
                    );
                if(userResourceAccessPermissionResponseEntity.getStatusCode().isError()) {
                    throw new ResponseErrorException("Error retrieving live streams");
                }
                List<UserResourceAccessPermission> userResourceAccessPermissions = userResourceAccessPermissionResponseEntity.getBody();
                if(userResourceAccessPermissions == null) {
                    throw new ResponseErrorException("Error retrieving live streams: null");
                }
                Set<Long> livestreamIds = userResourceAccessPermissions
                    .stream()
                    .map(UserResourceAccessPermission::getId)
                    .collect(Collectors.toSet());
                List<Livestream> livestreamList = livestreamRepository.findAllById(livestreamIds);
                livestreamPage = new PageImpl<>(livestreamList);
                break;
            case END_USER:
                break;
        }

        return livestreamPage;
    }


    /**
     * Get one livestream by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Livestream> findOne(Long id) throws ActionNotSupportedException {
        log.debug("Request to get Livestream : {}", id);

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(ENTITY_NAME);
        if(actionPermissionForRoleResponseEntity.getStatusCode().isError()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole == null) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        if(!actionPermissionForRole.isCanRead()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        return livestreamRepository.findById(id);
    }

    /**
     * Delete the livestream by id.
     *
     * @param id the id of the entity
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id, Long orgId) throws ActionNotSupportedException, ResourceNotFoundException {
        log.debug("Request to delete Livestream : {}", id);

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(ENTITY_NAME);
        if(actionPermissionForRoleResponseEntity.getStatusCode().isError()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole == null) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        if(!actionPermissionForRole.isCanDelete()) {
            throw new ActionNotSupportedException(ACTION_NOT_SUPPORTED_MESSAGE);
        }

        Livestream livestreamToBeDeleted = livestreamRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if(!livestreamToBeDeleted.getOrganizationId().equals(orgId)) {
            throw new ActionNotSupportedException("Action not permitted. You do not own the entity");
        }

        livestreamRepository.deleteById(id);
    }
}
