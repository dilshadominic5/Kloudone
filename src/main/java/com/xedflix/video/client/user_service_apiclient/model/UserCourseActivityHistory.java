package com.xedflix.video.client.user_service_apiclient.model;

import java.time.LocalDate;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.xedflix.video.client.user_service_apiclient.model.ExtendedUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserCourseActivityHistory
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-09-07T19:33:07.937+05:30[Asia/Kolkata]")

public class UserCourseActivityHistory   {
  @JsonProperty("activityType")
  private String activityType = null;

  @JsonProperty("courseId")
  private Long courseId = null;

  @JsonProperty("createdAt")
  private LocalDate createdAt = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("resourceId")
  private Long resourceId = null;

  @JsonProperty("resourceType")
  private String resourceType = null;

  @JsonProperty("user")
  private ExtendedUser user = null;

  public UserCourseActivityHistory activityType(String activityType) {
    this.activityType = activityType;
    return this;
  }

  /**
   * Get activityType
   * @return activityType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getActivityType() {
    return activityType;
  }

  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }

  public UserCourseActivityHistory courseId(Long courseId) {
    this.courseId = courseId;
    return this;
  }

  /**
   * Get courseId
   * @return courseId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public UserCourseActivityHistory createdAt(LocalDate createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public UserCourseActivityHistory id(Long id) {
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

  public UserCourseActivityHistory resourceId(Long resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getResourceId() {
    return resourceId;
  }

  public void setResourceId(Long resourceId) {
    this.resourceId = resourceId;
  }

  public UserCourseActivityHistory resourceType(String resourceType) {
    this.resourceType = resourceType;
    return this;
  }

  /**
   * Get resourceType
   * @return resourceType
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getResourceType() {
    return resourceType;
  }

  public void setResourceType(String resourceType) {
    this.resourceType = resourceType;
  }

  public UserCourseActivityHistory user(ExtendedUser user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public ExtendedUser getUser() {
    return user;
  }

  public void setUser(ExtendedUser user) {
    this.user = user;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserCourseActivityHistory userCourseActivityHistory = (UserCourseActivityHistory) o;
    return Objects.equals(this.activityType, userCourseActivityHistory.activityType) &&
        Objects.equals(this.courseId, userCourseActivityHistory.courseId) &&
        Objects.equals(this.createdAt, userCourseActivityHistory.createdAt) &&
        Objects.equals(this.id, userCourseActivityHistory.id) &&
        Objects.equals(this.resourceId, userCourseActivityHistory.resourceId) &&
        Objects.equals(this.resourceType, userCourseActivityHistory.resourceType) &&
        Objects.equals(this.user, userCourseActivityHistory.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activityType, courseId, createdAt, id, resourceId, resourceType, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserCourseActivityHistory {\n");
    
    sb.append("    activityType: ").append(toIndentedString(activityType)).append("\n");
    sb.append("    courseId: ").append(toIndentedString(courseId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    resourceType: ").append(toIndentedString(resourceType)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

