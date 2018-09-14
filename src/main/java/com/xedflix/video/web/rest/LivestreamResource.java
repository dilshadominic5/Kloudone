package com.xedflix.video.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.xedflix.video.domain.Livestream;
import com.xedflix.video.service.LivestreamService;
import com.xedflix.video.web.rest.errors.BadRequestAlertException;
import com.xedflix.video.web.rest.util.HeaderUtil;
import com.xedflix.video.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Livestream.
 */
@RestController
@RequestMapping("/api")
public class LivestreamResource {

    private final Logger log = LoggerFactory.getLogger(LivestreamResource.class);

    private static final String ENTITY_NAME = "livestream";

    private final LivestreamService livestreamService;

    public LivestreamResource(LivestreamService livestreamService) {
        this.livestreamService = livestreamService;
    }

    /**
     * POST  /livestreams : Create a new livestream.
     *
     * @param livestream the livestream to create
     * @return the ResponseEntity with status 201 (Created) and with body the new livestream, or with status 400 (Bad Request) if the livestream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/livestreams")
    @Timed
    public ResponseEntity<Livestream> createLivestream(@RequestBody Livestream livestream) throws URISyntaxException {
        log.debug("REST request to save Livestream : {}", livestream);
        if (livestream.getId() != null) {
            throw new BadRequestAlertException("A new livestream cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livestream result = livestreamService.save(livestream);
        return ResponseEntity.created(new URI("/api/livestreams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /livestreams : Updates an existing livestream.
     *
     * @param livestream the livestream to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated livestream,
     * or with status 400 (Bad Request) if the livestream is not valid,
     * or with status 500 (Internal Server Error) if the livestream couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/livestreams")
    @Timed
    public ResponseEntity<Livestream> updateLivestream(@RequestBody Livestream livestream) throws URISyntaxException {
        log.debug("REST request to update Livestream : {}", livestream);
        if (livestream.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livestream result = livestreamService.save(livestream);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, livestream.getId().toString()))
            .body(result);
    }

    /**
     * GET  /livestreams : get all the livestreams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of livestreams in body
     */
    @GetMapping("/livestreams")
    @Timed
    public ResponseEntity<List<Livestream>> getAllLivestreams(Pageable pageable) {
        log.debug("REST request to get a page of Livestreams");
        Page<Livestream> page = livestreamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/livestreams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /livestreams/:id : get the "id" livestream.
     *
     * @param id the id of the livestream to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the livestream, or with status 404 (Not Found)
     */
    @GetMapping("/livestreams/{id}")
    @Timed
    public ResponseEntity<Livestream> getLivestream(@PathVariable Long id) {
        log.debug("REST request to get Livestream : {}", id);
        Optional<Livestream> livestream = livestreamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livestream);
    }

    /**
     * DELETE  /livestreams/:id : delete the "id" livestream.
     *
     * @param id the id of the livestream to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/livestreams/{id}")
    @Timed
    public ResponseEntity<Void> deleteLivestream(@PathVariable Long id) {
        log.debug("REST request to delete Livestream : {}", id);
        livestreamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
