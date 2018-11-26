package com.xedflix.video.service.dto;

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
}
