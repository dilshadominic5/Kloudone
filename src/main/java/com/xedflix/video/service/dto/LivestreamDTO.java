package com.xedflix.video.service.dto;

import com.xedflix.video.domain.Livestream;

import java.time.ZonedDateTime;

public class LivestreamDTO {
    private Long id;

    private String name;

    private String description;

    private String streamKey;

    private Boolean isScheduled;

    private ZonedDateTime scheduledAt;

    private String imageUrl;

    private String recordedFileName;

    private ZonedDateTime startedAt;

    private ZonedDateTime endedAt;

    private Boolean hasStarted;

    private Boolean hasEnded;

    private Long userId;

    private Long organizationId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Boolean isArchived;

    private Boolean isPublic;

    private String streamUrl;

    private String recordedUrl;

    public LivestreamDTO(Livestream livestream) {
        this.id = livestream.getId();
        this.name = livestream.getName();
        this.description = livestream.getDescription();
        this.streamKey = livestream.getStreamKey();
        this.isScheduled = livestream.isIsScheduled();
        this.scheduledAt = livestream.getScheduledAt();
        this.imageUrl = livestream.getImageUrl();
        this.recordedFileName = livestream.getRecordedFileName();
        this.startedAt = livestream.getStartedAt();
        this.endedAt = livestream.getEndedAt();
        this.hasStarted = livestream.isHasStarted();
        this.hasEnded = livestream.isHasEnded();
        this.userId = livestream.getUserId();
        this.organizationId = livestream.getOrganizationId();
        this.createdAt = livestream.getCreatedAt();
        this.updatedAt = livestream.getUpdatedAt();
        this.isArchived = livestream.isIsArchived();
        this.isPublic = livestream.isIsPublic();
        this.streamUrl = livestream.getStreamUrl();
        this.recordedUrl = livestream.getRecordedUrl();
    }


}
