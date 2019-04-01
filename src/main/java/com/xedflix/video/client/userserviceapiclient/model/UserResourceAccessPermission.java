package com.xedflix.video.client.userserviceapiclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * UserResourceAccessPermission
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-10-15T18:42:14.703+05:30[Asia/Kolkata]")

public class UserResourceAccessPermission   {
  @JsonProperty("createdAt")
  private OffsetDateTime createdAt = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("permissionLastGivenBy")
  private Long permissionLastGivenBy = null;

  @JsonProperty("resourceId")
  private Long resourceId = null;

  /**
   * Gets or Sets resourceType
   */
  public enum ResourceTypeEnum {
    VIDEO("Video"),

    COURSE("Course"),

    COURSERESOURCE("CourseResource"),

    COURSEMODULE("CourseModule");

    private String value;

    ResourceTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ResourceTypeEnum fromValue(String text) {
      for (ResourceTypeEnum b : ResourceTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("resourceType")
  private ResourceTypeEnum resourceType = null;

  @JsonProperty("userId")
  private Long userId = null;

  public UserResourceAccessPermission createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UserResourceAccessPermission id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UserResourceAccessPermission permissionLastGivenBy(Long permissionLastGivenBy) {
    this.permissionLastGivenBy = permissionLastGivenBy;
    return this;
  }

  /**
   * Get permissionLastGivenBy
   * @return permissionLastGivenBy
  **/
  @ApiModelProperty(value = "")


  public Long getPermissionLastGivenBy() {
    return permissionLastGivenBy;
  }

  public void setPermissionLastGivenBy(Long permissionLastGivenBy) {
    this.permissionLastGivenBy = permissionLastGivenBy;
  }

  public UserResourceAccessPermission resourceId(Long resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
  **/
  @ApiModelProperty(value = "")


  public Long getResourceId() {
    return resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }

  public UserResourceAccessPermission resourceType(ResourceTypeEnum resourceType) {
    this.resourceType = resourceType;
    return this;
  }

  /**
   * Get resourceType
   * @return resourceType
  **/
  @ApiModelProperty(value = "")


  public ResourceTypeEnum getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceTypeEnum resourceType) {
    this.resourceType = resourceType;
  }

  public UserResourceAccessPermission userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(value = "")


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserResourceAccessPermission userResourceAccessPermission = (UserResourceAccessPermission) o;
    return Objects.equals(this.createdAt, userResourceAccessPermission.createdAt) &&
        Objects.equals(this.id, userResourceAccessPermission.id) &&
        Objects.equals(this.permissionLastGivenBy, userResourceAccessPermission.permissionLastGivenBy) &&
        Objects.equals(this.resourceId, userResourceAccessPermission.resourceId) &&
        Objects.equals(this.resourceType, userResourceAccessPermission.resourceType) &&
        Objects.equals(this.userId, userResourceAccessPermission.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, id, permissionLastGivenBy, resourceId, resourceType, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserResourceAccessPermission {\n");

    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    permissionLastGivenBy: ").append(toIndentedString(permissionLastGivenBy)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    resourceType: ").append(toIndentedString(resourceType)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

