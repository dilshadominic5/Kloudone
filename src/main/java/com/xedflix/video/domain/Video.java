package com.xedflix.video.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "jhi_size")
    private Integer size;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_archived")
    private Boolean isArchived;

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

    public Video name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public Video fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public Video description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public Video url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUserId() {
        return userId;
    }

    public Video userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Video organizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Video imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSize() {
        return size;
    }

    public Video size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Float getDuration() {
        return duration;
    }

    public Video duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Video createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Video updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isIsArchived() {
        return isArchived;
    }

    public Video isArchived(Boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Video video = (Video) o;
        if (video.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), video.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", userId=" + getUserId() +
            ", organizationId=" + getOrganizationId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", size=" + getSize() +
            ", duration=" + getDuration() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", isArchived='" + isIsArchived() + "'" +
            "}";
    }

    public static Video merge(Video object, Video into) throws IllegalAccessException, InstantiationException {

        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("serialVersionUID");
        ignoreList.add("userId");
        ignoreList.add("organizationId");
        ignoreList.add("createdAt");

        List<String> notAccessList = new ArrayList<>();
        notAccessList.add("serialVersionUID");

        Class<Video> videoClass = Video.class;
        Field[] fields = videoClass.getDeclaredFields();
        Video video = videoClass.newInstance();
        for (Field field: fields) {
            if(!notAccessList.contains(field.getName())) {
                field.setAccessible(true);
                Object value1 = field.get(object);
                Object value2 = field.get(into);
                Object value = (value1 != null && !ignoreList.contains(field.getName())) ? value1 : value2;
                field.set(video, value);
            }
        }

        return video;
    }

}
