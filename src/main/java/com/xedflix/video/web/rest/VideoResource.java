package com.xedflix.video.web.rest;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.codahale.metrics.annotation.Timed;
import com.xedflix.video.config.ApplicationProperties;
import com.xedflix.video.domain.Video;
import com.xedflix.video.security.SecurityUtils;
import com.xedflix.video.service.VideoService;
import com.xedflix.video.service.exceptions.ActionNotSupportedException;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import com.xedflix.video.web.rest.errors.BadRequestAlertException;
import com.xedflix.video.web.rest.util.HeaderUtil;
import com.xedflix.video.web.rest.util.PaginationUtil;
import com.xedflix.video.web.rest.vm.PresignedUrlVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    @Autowired
    private ApplicationProperties applicationProperties;

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    private final VideoService videoService;

    public VideoResource(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * POST  /videos : Create a new video.
     *
     * @param video the video to create
     * @return the ResponseEntity with status 201 (Created) and with body the new video, or with status 400 (Bad Request) if the video has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/videos")
    @Timed
    public ResponseEntity<Video> createVideo(@Valid @RequestBody Video video) throws URISyntaxException, ActionNotSupportedException {
        log.debug("REST request to save Video : {}", video);
        if (video.getId() != null) {
            throw new BadRequestAlertException("A new video cannot already have an ID", ENTITY_NAME, "idexists");
        }

        video.setUserId(SecurityUtils.getCurrentUserId());
        video.setOrganizationId(SecurityUtils.getCurrentUserOrganizationId());

        Video result = videoService.save(video);
        return ResponseEntity.created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videos : Updates an existing video.
     *
     * @param video the video to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated video,
     * or with status 400 (Bad Request) if the video is not valid,
     * or with status 500 (Internal Server Error) if the video couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/videos")
    @Timed
    public ResponseEntity<Video> updateVideo(@RequestBody Video video) throws URISyntaxException, ActionNotSupportedException, IllegalAccessException, InstantiationException, ResourceNotFoundException {
        log.debug("REST request to update Video : {}", video);
        if (video.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Video result = videoService.update(video);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, video.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videos : get all the videos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of videos in body
     */
    @GetMapping("/videos")
    @Timed
    public ResponseEntity<List<Video>> getAllVideos(Pageable pageable) throws ActionNotSupportedException {
        log.debug("REST request to get a page of Videos");
        Page<Video> page = videoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/videos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /videos/:id : get the "id" video.
     *
     * @param id the id of the video to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the video, or with status 404 (Not Found)
     */
    @GetMapping("/videos/{id}")
    @Timed
    public ResponseEntity<Video> getVideo(@PathVariable Long id) throws ActionNotSupportedException {
        log.debug("REST request to get Video : {}", id);
        Optional<Video> video = videoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(video);
    }

    /**
     * DELETE  /videos/:id : delete the "id" video.
     *
     * @param id the id of the video to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/videos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) throws ActionNotSupportedException {
        log.debug("REST request to delete Video : {}", id);
        videoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * Generate a presigned url for a file to be uploaded to S3 Bucket.
     *
     * @param fileName The filename of the file to be uploaded
     * @param type The mime type of the file.
     * @return the ResponseEntity with status 200 (OK)
     */
    // TODO: Need to move to service.
    @GetMapping("/videos/generate-pre-signed-url")
    public ResponseEntity<PresignedUrlVM> generatePreSignedUrl(@RequestParam(value = "filename") String fileName, @RequestParam( value = "type") String type) {
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

        PresignedUrlVM presignedUrlVM = new PresignedUrlVM();
        presignedUrlVM.setUrl(url.toString());
        presignedUrlVM.setNewName(uuidString);
        presignedUrlVM.setExpiresIn(expiration.getTime());

        return ResponseEntity.ok(presignedUrlVM);
    }
}
