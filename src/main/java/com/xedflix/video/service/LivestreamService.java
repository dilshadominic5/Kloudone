package com.xedflix.video.service;

import com.xedflix.video.domain.Livestream;
import com.xedflix.video.repository.LivestreamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Livestream.
 */
@Service
@Transactional
public class LivestreamService {

    private final Logger log = LoggerFactory.getLogger(LivestreamService.class);

    private final LivestreamRepository livestreamRepository;

    public LivestreamService(LivestreamRepository livestreamRepository) {
        this.livestreamRepository = livestreamRepository;
    }

    /**
     * Save a livestream.
     *
     * @param livestream the entity to save
     * @return the persisted entity
     */
    public Livestream save(Livestream livestream) {
        log.debug("Request to save Livestream : {}", livestream);        return livestreamRepository.save(livestream);
    }

    /**
     * Get all the livestreams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Livestream> findAll(Pageable pageable) {
        log.debug("Request to get all Livestreams");
        return livestreamRepository.findAll(pageable);
    }


    /**
     * Get one livestream by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Livestream> findOne(Long id) {
        log.debug("Request to get Livestream : {}", id);
        return livestreamRepository.findById(id);
    }

    /**
     * Delete the livestream by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Livestream : {}", id);
        livestreamRepository.deleteById(id);
    }
}
