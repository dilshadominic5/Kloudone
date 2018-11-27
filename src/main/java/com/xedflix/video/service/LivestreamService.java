package com.xedflix.video.service;

import com.google.common.hash.Hashing;
import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.api.UserResourceAccessPermissionResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserResourceAccessPermission;
import com.xedflix.video.domain.Livestream;
import com.xedflix.video.repository.LivestreamRepository;
import com.xedflix.video.security.UserRole;
import com.xedflix.video.service.dto.LivestreamDTO;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import com.xedflix.video.service.exceptions.ResponseErrorException;
import com.xedflix.video.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.nio.charset.StandardCharsets;
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

    private static final boolean IS_LIVE = true;
    private static final boolean IS_NOT_LIVE = false;

    private static final boolean HAS_ENDED = true;
    private static final boolean HAS_NOT_ENDED = false;

    private static final boolean HAS_STARTED = true;
    private static final boolean HAS_NOT_STARTED = false;

    private static final boolean IS_SCHEDULED = true;
    private static final boolean IS_NOT_SCHEDULED = true;

    private final Logger log = LoggerFactory.getLogger(LivestreamService.class);

    private final LivestreamRepository livestreamRepository;

    @Autowired
    private RoleResourceApiClient roleResourceApiClient;

    @Autowired
    private UserResourceAccessPermissionResourceApiClient userResourceAccessPermissionResourceApiClient;

    public LivestreamService(LivestreamRepository livestreamRepository) {
        this.livestreamRepository = livestreamRepository;
    }

    @Value("${application.livestream.application_name}")
    private String applicationName;

    @Value("${application.livestream.ingestion.rtmp.base_url}")
    private String rtmpBaseUrl;

    @Value("${application.livestream.delivery.live.hls_base_url}")
    private String liveHlsStreamUrl;

    @Value("${application.livestream.delivery.live.dash_base_url}")
    private String liveDashStreamUrl;

    @Value("${application.livestream.delivery.recorded.base_url}")
    private String liveRecordedVideoUrl;

    @Value("${application.livestream.delivery.live.rtmp_stream_base_url}")
    private String liveRtmpStreamUrl;

    public String makeRecordedUrl(String name) { return liveRecordedVideoUrl + "/" + name; }
    public String makeHLSStreamUrl(String key) {
        return liveHlsStreamUrl + "/" + key + ".m3u8";
    }
    public String makeDASHStreamUrl(String key) {
        return liveDashStreamUrl + "/" + key + "/index.mpd";
    }
    public String makeRTMPStreamUrl(String key) { return liveRtmpStreamUrl + "/" + key; }

    public static String generateStreamKey(String resource, String resourceId, String userId) {

        String hashString = resource + resourceId + userId + System.currentTimeMillis();

        return Hashing.sha256().hashString(hashString, StandardCharsets.UTF_8).toString();
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

        if(livestream.isIsScheduled() == null) {
            livestream.setIsScheduled(false);
        }

        if(livestream.isIsPublic() == null) {
            livestream.setIsPublic(false);
        }

        if(livestream.isIsArchived() == null) {
            livestream.setIsArchived(false);
        }

        livestream.setHasStarted(false);
        livestream.setHasEnded(false);
        livestream.setStreamKey(
            generateStreamKey(ENTITY_NAME, "RANDOM", String.valueOf(livestream.getUserId()))
        );

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
    public Page<LivestreamDTO> findAll(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
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

        List<LivestreamDTO> livestreamDTOS = livestreamPage.stream().map(livestream -> {
            LivestreamDTO livestreamDTO = new LivestreamDTO(livestream);
            livestreamDTO.setStreamUrl(rtmpBaseUrl);
            livestreamDTO.setDashUrl(makeDASHStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setHlsUrl(makeHLSStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setRtmpUrl(makeRTMPStreamUrl(livestream.getStreamKey()));
            return livestreamDTO;
        }).collect(Collectors.toList());

        Page<LivestreamDTO> livestreamDTOPage = new PageImpl<>(livestreamDTOS, pageable, livestreamPage.getTotalPages());

        return livestreamDTOPage;
    }

    /**
     * Get currently live streams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LivestreamDTO> findLiveStreams(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
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
                livestreamPage = livestreamRepository.findByHasStartedAndHasEnded(HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_USER:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case END_USER:
                break;
        }

        List<LivestreamDTO> livestreamDTOS = livestreamPage.stream().map(livestream -> {
            LivestreamDTO livestreamDTO = new LivestreamDTO(livestream);
            livestreamDTO.setStreamUrl(rtmpBaseUrl);
            livestreamDTO.setDashUrl(makeDASHStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setHlsUrl(makeHLSStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setRtmpUrl(makeRTMPStreamUrl(livestream.getStreamKey()));
            return livestreamDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(livestreamDTOS, pageable, livestreamPage.getTotalPages());
    }

    /**
     * Get Unused live streams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Livestream> findUnUsedStreams(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
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
                livestreamPage = livestreamRepository.findByHasStartedAndHasEnded(HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(organizationId, IS_NOT_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(organizationId, IS_NOT_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_USER:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(organizationId, IS_NOT_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case END_USER:
                break;
        }

        for (Livestream livestream : livestreamPage) {
            livestream.setStreamUrl(rtmpBaseUrl);
        }

        return livestreamPage;
    }

    /**
     * Get past streams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LivestreamDTO> findPastStreams(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
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
                livestreamPage = livestreamRepository.findByHasStartedAndHasEnded(HAS_STARTED, HAS_ENDED, pageable);
                break;
            case ORG_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_ENDED, pageable);
                break;
            case ORG_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_ENDED, pageable);
                break;
            case ORG_USER:
                livestreamPage = livestreamRepository.findByOrganizationIdAndHasStartedAndHasEnded(organizationId, HAS_STARTED, HAS_ENDED, pageable);
                break;
            case END_USER:
                break;
        }

        List<LivestreamDTO> livestreamDTOS = livestreamPage.stream().map(livestream -> {
            LivestreamDTO livestreamDTO = new LivestreamDTO(livestream);
            livestreamDTO.setStreamUrl(rtmpBaseUrl);
            livestreamDTO.setDashUrl(makeDASHStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setHlsUrl(makeHLSStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setRtmpUrl(makeRTMPStreamUrl(livestream.getStreamKey()));
            livestreamDTO.setRecordedUrl(makeRecordedUrl(livestream.getRecordedFileName()));
            return livestreamDTO;
        }).collect(Collectors.toList());

        return new PageImpl<>(livestreamDTOS, pageable, livestreamPage.getTotalPages());
    }

    /**
     * Get scheduled streams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Livestream> findScheduledStreams(Pageable pageable, Long organizationId, Long userId, UserRole userRole) throws ActionNotSupportedException, ResponseErrorException {
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
                livestreamPage = livestreamRepository.findByIsScheduledAndHasStartedAndHasEnded(IS_SCHEDULED, HAS_STARTED, HAS_NOT_ENDED, pageable);
                break;
            case ORG_SUPER_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(
                    organizationId, IS_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable
                );
                break;
            case ORG_ADMIN:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(
                    organizationId, IS_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable
                );
                break;
            case ORG_USER:
                livestreamPage = livestreamRepository.findByOrganizationIdAndIsScheduledAndHasStartedAndHasEnded(
                    organizationId, IS_SCHEDULED, HAS_NOT_STARTED, HAS_NOT_ENDED, pageable
                );
                break;
            case END_USER:
                break;
        }

        for (Livestream livestream : livestreamPage) {
            livestream.setStreamUrl(rtmpBaseUrl);
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

    public void onRTMPPublish(String call, String app, String name) throws ActionNotSupportedException, ResourceNotFoundException, IllegalAccessException, InstantiationException {
        if(!call.equals("publish") || !app.equals(applicationName)) {
            throw new ActionNotSupportedException();
        }

        Livestream livestream = livestreamRepository.findByStreamKey(name).orElseThrow(ResourceNotFoundException::new);
        livestream.setHasStarted(true);
        livestream.setStartedAt(ZonedDateTime.now(ZoneId.of("UTC")));
        update(livestream);
    }

    public void onRTMPEnd(String call, String name) throws ActionNotSupportedException, ResourceNotFoundException, IllegalAccessException, InstantiationException {
        if(!call.equals("done")) {
            throw new ActionNotSupportedException();
        }

        Livestream livestream = livestreamRepository.findByStreamKey(name).orElseThrow(ResourceNotFoundException::new);
        livestream.setHasEnded(true);
        livestream.setEndedAt(ZonedDateTime.now(ZoneId.of("UTC")));
        update(livestream);
    }

    public void onRTMPRecordDone(String call, String name, String path) throws ActionNotSupportedException, ResourceNotFoundException, IllegalAccessException, InstantiationException {
        if(!call.equals("record_done")) {
            throw new ActionNotSupportedException();
        }

        Livestream livestream = livestreamRepository.findByStreamKey(name).orElseThrow(ResourceNotFoundException::new);

        String[] pathSplit = path.split("/");
        String recordedFileName = pathSplit[pathSplit.length - 1];
        recordedFileName = StringUtils.replaceLast(recordedFileName, "flv", "mp4");

        livestream.setRecordedFileName(recordedFileName);
        update(livestream);
    }
}
