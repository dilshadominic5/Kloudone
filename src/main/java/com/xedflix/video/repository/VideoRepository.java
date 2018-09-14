package com.xedflix.video.repository;

import com.xedflix.video.domain.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Spring Data  repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    public Page<Video> findAllByUserIdAndOrganizationId(Long userId, Long organizationId, Pageable pageable);

    public Optional<Video> findOneByIdAndOrganizationId(Long userId, Long organizationId);

}
