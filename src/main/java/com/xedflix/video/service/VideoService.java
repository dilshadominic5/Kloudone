package com.xedflix.video.service;

import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.domain.Video;
import com.xedflix.video.repository.VideoRepository;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Video.
 */
@Service
@Transactional
public class VideoService {

    private static final String VIDEO_ACTION_ITEM_NAME = "Video";

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final VideoRepository videoRepository;

    @Autowired
    private RoleResourceApiClient roleResourceApiClient;

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
    public Video update(Video video) throws ActionNotSupportedException {
        log.debug("Request to save Video : {}", video);
        ResponseEntity<ActionPermissionForRole> actionPermissionForRoleResponseEntity =
            roleResourceApiClient.getPermissionForRoleOnActionItemUsingGET(VIDEO_ACTION_ITEM_NAME);

        ActionPermissionForRole actionPermissionForRole = actionPermissionForRoleResponseEntity.getBody();
        if(actionPermissionForRole != null && !actionPermissionForRole.isCanUpdate()) {
            throw new ActionNotSupportedException();
        }

        return videoRepository.save(video);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Video> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable);
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
     * Delete the video by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
