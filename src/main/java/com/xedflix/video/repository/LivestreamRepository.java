package com.xedflix.video.repository;

import com.xedflix.video.domain.Livestream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Livestream entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivestreamRepository extends JpaRepository<Livestream, Long> {
    Page<Livestream> findByOrganizationId(Long organizationId, Pageable pageable);
}
