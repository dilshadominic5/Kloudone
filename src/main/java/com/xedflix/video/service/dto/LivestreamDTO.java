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

    private String hlsUrl;

    private String dashUrl;

    private String rtmpUrl;

    private String embedCode;

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
        this.embedCode = livestream.getEmbedCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public Boolean getScheduled() {
        return isScheduled;
    }

    public void setScheduled(Boolean scheduled) {
        isScheduled = scheduled;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(ZonedDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecordedFileName() {
        return recordedFileName;
    }

    public void setRecordedFileName(String recordedFileName) {
        this.recordedFileName = recordedFileName;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public ZonedDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(ZonedDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Boolean getHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(Boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public Boolean getHasEnded() {
        return hasEnded;
    }

    public void setHasEnded(Boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getRecordedUrl() {
        return recordedUrl;
    }

    public void setRecordedUrl(String recordedUrl) {
        this.recordedUrl = recordedUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public String getDashUrl() {
        return dashUrl;
    }

    public void setDashUrl(String dashUrl) {
        this.dashUrl = dashUrl;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public void setEmbedCode(String embedCode) {
        this.embedCode = embedCode;
    }
}
