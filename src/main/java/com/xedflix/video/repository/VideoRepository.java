package com.xedflix.video.repository;

import com.xedflix.video.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    public Page<Video> findAllByUserIdAndOrganizationId(Long userId, Long organizationId, Pageable pageable);

    public Optional<Video> findOneByIdAndOrganizationId(Long userId, Long organizationId);

    public Page<Video> findAllByNameIgnoreCaseContainingAndOrganizationId(String name, Long organizationId, Pageable pageable);

    @Query(value = "SELECT * from video where MATCH(name) AGAINST(?1) AND organization_id=?2 ORDER BY ?#{#pageable}",
        countQuery = "SELECT * from video where MATCH(name) AGAINST(?1) AND organization_id=?2",
        nativeQuery = true)
    public Page<Video> fullTextSearch(String queryString, Long organizationId, Pageable pageable);

}
