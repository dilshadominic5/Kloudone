package com.xedflix.video.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.apigateway.model.Op;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.config.ApplicationProperties;
import com.xedflix.video.domain.Video;
import com.xedflix.video.repository.VideoRepository;
import com.xedflix.video.security.SecurityUtils;
import com.xedflix.video.service.dto.PresignedUrlDTO;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing Video.
 */
@RequiredArgsConstructor
@Service
@Transactional
public class VideoService {

    private static final String VIDEO_ACTION_ITEM_NAME = "Video";

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final VideoRepository videoRepository;

    @Autowired
    private RoleResourceApiClient roleResourceApiClient;

    @Autowired
    private ApplicationProperties applicationProperties;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    /**
     * Save a video.
     *
     * @param video the entity to save
     * @return the persisted entity
     */
    public Video save(Video video) throws ActionNotSupportedException {
        log.debug("Request to save Video : {}", video);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanCreate()) {
            throw new ActionNotSupportedException();
        }

        return videoRepository.save(video);
    }

    /**
     * Update a video.
     *
     * @param video the entity to update
     * @return the persisted entity
     */
    public Video update(Video video) throws ActionNotSupportedException, InstantiationException, IllegalAccessException, ResourceNotFoundException {
        log.debug("Request to save Video : {}", video);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanUpdate()) {
            throw new ActionNotSupportedException();
        }

        Video videoToUpdate = videoRepository.findOneByIdAndOrganizationId(video.getId(), SecurityUtils.getCurrentUserOrganizationId()).orElseThrow(ResourceNotFoundException::new);
        Video newVideo = Video.merge(video, videoToUpdate);

        log.debug("New video: {}", newVideo);

        return videoRepository.save(newVideo);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) throws ActionNotSupportedException {
        log.debug("Request to get all Videos");

        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanRead()) {
            throw new ActionNotSupportedException();
        }

        Long userId = SecurityUtils.getCurrentUserId();
        Long organizationId = SecurityUtils.getCurrentUserOrganizationId();

        return videoRepository.findAllByUserIdAndOrganizationId(userId, organizationId, pageable);
    }


    /**
     * Get one video by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Video> findOne(Long id) throws ActionNotSupportedException {
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
}
