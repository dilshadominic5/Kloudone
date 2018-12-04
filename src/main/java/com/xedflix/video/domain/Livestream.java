package com.xedflix.video.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Livestream.
 */
@Entity
@Table(name = "livestream")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Livestream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stream_key")
    private String streamKey;

    @Column(name = "is_scheduled")
    private Boolean isScheduled;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "recorded_file_name")
    private String recordedFileName;

    @Column(name = "has_started")
    private Boolean hasStarted;

    @Column(name = "has_ended")
    private Boolean hasEnded;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "stream_url")
    private String streamUrl;

    @Column(name = "recorded_url")
    private String recordedUrl;

    @Column(name = "scheduled_at")
    private ZonedDateTime scheduledAt;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "started_at")
    private ZonedDateTime startedAt;

    @Column(name = "ended_at")
    private ZonedDateTime endedAt;

    @Transient
    private String embedCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Livestream name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Livestream description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public Livestream streamKey(String streamKey) {
        this.streamKey = streamKey;
        return this;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public Boolean isIsScheduled() {
        return isScheduled;
    }

    public Livestream isScheduled(Boolean isScheduled) {
        this.isScheduled = isScheduled;
        return this;
    }

    public void setIsScheduled(Boolean isScheduled) {
        this.isScheduled = isScheduled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Livestream imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecordedFileName() {
        return recordedFileName;
    }

    public Livestream recordedFileName(String recordedFileName) {
        this.recordedFileName = recordedFileName;
        return this;
    }

    public void setRecordedFileName(String recordedFileName) {
        this.recordedFileName = recordedFileName;
    }

    public Boolean isHasStarted() {
        return hasStarted;
    }

    public Livestream hasStarted(Boolean hasStarted) {
        this.hasStarted = hasStarted;
        return this;
    }

    public void setHasStarted(Boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public Boolean isHasEnded() {
        return hasEnded;
    }

    public Livestream hasEnded(Boolean hasEnded) {
        this.hasEnded = hasEnded;
        return this;
    }

    public void setHasEnded(Boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    public Long getUserId() {
        return userId;
    }

    public Livestream userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Livestream organizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean isIsArchived() {
        return isArchived;
    }

    public Livestream isArchived(Boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public Livestream isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public Livestream streamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
        return this;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getRecordedUrl() {
        return recordedUrl;
    }

    public Livestream recordedUrl(String recordedUrl) {
        this.recordedUrl = recordedUrl;
        return this;
    }

    public void setRecordedUrl(String recordedUrl) {
        this.recordedUrl = recordedUrl;
    }

    public ZonedDateTime getScheduledAt() {
        return scheduledAt;
    }

    public Livestream scheduledAt(ZonedDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
        return this;
    }

    public void setScheduledAt(ZonedDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Livestream createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Livestream updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getStartedAt() {
        return startedAt;
    }

    public Livestream startedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public void setStartedAt(ZonedDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public ZonedDateTime getEndedAt() {
        return endedAt;
    }

    public Livestream endedAt(ZonedDateTime endedAt) {
        this.endedAt = endedAt;
        return this;
    }

    public void setEndedAt(ZonedDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public void setEmbedCode(String embedCode) {
        this.embedCode = embedCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public static Livestream merge(Livestream object, Livestream into) throws IllegalAccessException, InstantiationException {

        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("serialVersionUID");
        ignoreList.add("userId");
        ignoreList.add("organizationId");
        ignoreList.add("createdAt");

        List<String> notAccessList = new ArrayList<>();
        notAccessList.add("serialVersionUID");

        Class<Livestream> livestreamClass = Livestream.class;
        Field[] fields = livestreamClass.getDeclaredFields();
        Livestream live = livestreamClass.newInstance();
        for (Field field: fields) {
            if(!notAccessList.contains(field.getName())) {
                field.setAccessible(true);
                Object value1 = field.get(object);
                Object value2 = field.get(into);
                Object value = (value1 != null && !ignoreList.contains(field.getName())) ? value1 : value2;
                field.set(live, value);
            }
        }

        return live;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Livestream livestream = (Livestream) o;
        if (livestream.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), livestream.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Livestream{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", streamKey='" + getStreamKey() + "'" +
            ", isScheduled='" + isIsScheduled() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", recordedFileName='" + getRecordedFileName() + "'" +
            ", hasStarted='" + isHasStarted() + "'" +
            ", hasEnded='" + isHasEnded() + "'" +
            ", userId=" + getUserId() +
            ", organizationId=" + getOrganizationId() +
            ", isArchived='" + isIsArchived() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", streamUrl='" + getStreamUrl() + "'" +
            ", recordedUrl='" + getRecordedUrl() + "'" +
            ", scheduledAt='" + getScheduledAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", startedAt='" + getStartedAt() + "'" +
            ", endedAt='" + getEndedAt() + "'" +
            "}";
    }
}
