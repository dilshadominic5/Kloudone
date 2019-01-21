package com.xedflix.video.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.api.UserResourceAccessPermissionResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserResourceAccessPermission;
import com.xedflix.video.config.ApplicationProperties;
import com.xedflix.video.domain.Video;
import com.xedflix.video.repository.VideoRepository;
import com.xedflix.video.security.SecurityUtils;
import com.xedflix.video.security.UserRole;
import com.xedflix.video.service.dto.PresignedUrlDTO;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Video.
 */
@Service
@Transactional
public class VideoService {

    private static final String VIDEO_ACTION_ITEM_NAME = "Video";

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RoleResourceApiClient roleResourceApiClient;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private UserResourceAccessPermissionResourceApiClient userResourceAccessPermissionResourceApiClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${application.kafka.video-processing.topic}")
    private String videoProcessorTopic;

    /**
     * Save a video.
     *
     * @param video the entity to create
     * @return the persisted entity
     */
    public Video save(Video video) throws ActionNotSupportedException {
        log.debug("Request to create Video : {}", video);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanCreate()) {
            throw new ActionNotSupportedException();
        }

        video.setUrl(applicationProperties.getCloudfront().getBaseUrl() + "/" + video.getFileName());
        LocalDate localDate = LocalDate.now(ZoneId.of("UTC"));
        video.setCreatedAt(localDate);
        Video savedVideo = videoRepository.save(video);

        // Send a message of the created video's ID so that the metadata such as length and thumbnail are created
        kafkaTemplate.send(videoProcessorTopic, String.valueOf(savedVideo.getId()));

        return savedVideo;
    }

    /**
     * Save a video.
     *
     * @param videos the entity to create
     * @return the persisted entity
     */
    public List<Video> save(List<Video> videos) throws ActionNotSupportedException {
        log.debug("Request to create Video : {}", videos);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanCreate()) {
            throw new ActionNotSupportedException();
        }

        videos.forEach(video -> {
            LocalDate localDate = LocalDate.now(ZoneId.of("UTC"));
            video.setCreatedAt(localDate);
        });

        return videoRepository.saveAll(videos);
    }

    /**
     * Update a video.
     *
     * @param video the entity to update
     * @return the persisted entity
     */
    public Video update(Video video) throws ActionNotSupportedException, InstantiationException, IllegalAccessException, ResourceNotFoundException {
        log.debug("Request to create Video : {}", video);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanUpdate()) {
            throw new ActionNotSupportedException();
        }

        Video videoToUpdate = videoRepository.findOneByIdAndOrganizationId(video.getId(), SecurityUtils.getCurrentUserOrganizationId()).orElseThrow(ResourceNotFoundException::new);
        Video newVideo = Video.merge(video, videoToUpdate);

        log.debug("New video: {}", newVideo);
        newVideo.setUpdatedAt(LocalDate.now(ZoneId.of("UTC")));
        return videoRepository.save(newVideo);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) throws Exception {
        log.debug("Request to get all Videos");

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanRead()) {
            throw new ActionNotSupportedException();
        }

        Page<Video> videoPage = Page.empty();

        String role = SecurityUtils.getCurrentUserRole();
        UserRole userRole = UserRole.valueOf(role);

        Long userId = SecurityUtils.getCurrentUserId();
        Long organizationId = SecurityUtils.getCurrentUserOrganizationId();

        switch (userRole) {
            case ORG_USER:
                ResponseEntity<List<UserResourceAccessPermission>> userResourceAccessPermissionResponseEntity =
                    userResourceAccessPermissionResourceApiClient.findResourcesUsingGET(
                        VIDEO_ACTION_ITEM_NAME,
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
                    throw new Exception("Error retrieving Videos");
                }
                List<UserResourceAccessPermission> userResourceAccessPermissions = userResourceAccessPermissionResponseEntity.getBody();
                if(userResourceAccessPermissions == null) {
                    throw new Exception("Error retrieving Videos: null");
                }
                log.debug("UserResourceAccessPermission permissions: {}", userResourceAccessPermissions);
                Set<Long> videoIds = userResourceAccessPermissions
                    .stream()
                    .map(UserResourceAccessPermission::getResourceId)
                    .collect(Collectors.toSet());
                log.debug("Video Ids: {}", videoIds);
                List<Video> videoList = videoRepository.findAllById(videoIds);
                videoPage = new PageImpl<>(videoList);
                break;
            case XEDFLIX_SUPER_ADMIN:
                videoPage = videoRepository.findAll(pageable);
                break;
            case END_USER:
                break;
            case ORG_ADMIN:
                videoPage = videoRepository.findAllByUserIdAndOrganizationId(userId, organizationId, pageable);
                break;
            case ORG_SUPER_ADMIN:
                videoPage = videoRepository.findAllByUserIdAndOrganizationId(userId, organizationId, pageable);
                break;
        }

        return videoPage;
    }


    /**
     * Get one video by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Video> findOneByOrganization(Long id) throws ActionNotSupportedException {
        log.debug("Request to get Video : {}", id);

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanRead()) {
            throw new ActionNotSupportedException();
        }

        return videoRepository.findOneByIdAndOrganizationId(id, SecurityUtils.getCurrentUserOrganizationId());
    }

    /**
     * Get one video by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Video> findOne(Long id) {
        log.debug("Request to get Video : {}", id);

        return videoRepository.findById(id);
    }

    /**
     * Get one video by id.
     *
     * @param ids the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<Video> findByIds(List<Long> ids) {
        log.debug("Request to get Video : {}", ids);

        return videoRepository.findAllById(ids);
    }

    /**
     * Delete the video by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) throws ActionNotSupportedException, ResourceNotFoundException {
        log.debug("Request to delete Video : {}", id);

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        // Throw exception if the user is not allowed to perform the action.
        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanDelete()) {
            throw new ActionNotSupportedException();
        }

        Long orgId = SecurityUtils.getCurrentUserOrganizationId();
        System.out.println(id);
        System.out.println(orgId);

        videoRepository.findOneByIdAndOrganizationId(id, orgId).orElseThrow(ResourceNotFoundException::new);
        videoRepository.deleteById(id);
    }

    public PresignedUrlDTO generatePresignedUrl(@NotNull String fileName) throws ActionNotSupportedException {

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanCreate()) {
            throw new ActionNotSupportedException();
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new ProfileCredentialsProvider())
            .withRegion(applicationProperties.getS3().getUserVideoUploads().getRegion())
            .build();

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String extension = FilenameUtils.getExtension(fileName);
        uuidString += "." + extension;

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        // Expire after ten minutes
        expTimeMillis += 1000 * 10 * 60;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(applicationProperties.getS3().getUserVideoUploads().getBucket(), uuidString)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        PresignedUrlDTO presignedUrlDTO = new PresignedUrlDTO();
        presignedUrlDTO.setUrl(url.toString());
        presignedUrlDTO.setNewName(uuidString);
        presignedUrlDTO.setExpiresIn(expiration.getTime());

        return presignedUrlDTO;
    }

    public Page<Video> search(String queryString, Long organizationId, Pageable pageable) {
        return videoRepository.findAllByNameIgnoreCaseContainingAndOrganizationId(queryString, organizationId, pageable);
    }
}
