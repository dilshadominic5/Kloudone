package com.xedflix.video.client.userserviceapiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ExtendedUser
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-09-07T19:33:07.937+05:30[Asia/Kolkata]")

public class ExtendedUser   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("permissionOnEntities")
  @Valid
  private List<PermissionOnEntity> permissionOnEntities = null;

  @JsonProperty("phone")
  private String phone = null;

  @JsonProperty("userCourseActivityHistories")
  @Valid
  private List<UserCourseActivityHistory> userCourseActivityHistories = null;

  @JsonProperty("userId")
  private Long userId = null;

  public ExtendedUser id(Long id) {
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

  public ExtendedUser permissionOnEntities(List<PermissionOnEntity> permissionOnEntities) {
    this.permissionOnEntities = permissionOnEntities;
    return this;
  }

  public ExtendedUser addPermissionOnEntitiesItem(PermissionOnEntity permissionOnEntitiesItem) {
    if (this.permissionOnEntities == null) {
      this.permissionOnEntities = new ArrayList<PermissionOnEntity>();
    }
    this.permissionOnEntities.add(permissionOnEntitiesItem);
    return this;
  }

  /**
   * Get permissionOnEntities
   * @return permissionOnEntities
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PermissionOnEntity> getPermissionOnEntities() {
    return permissionOnEntities;
  }

  public void setPermissionOnEntities(List<PermissionOnEntity> permissionOnEntities) {
    this.permissionOnEntities = permissionOnEntities;
  }

  public ExtendedUser phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Get phone
   * @return phone
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public ExtendedUser userCourseActivityHistories(List<UserCourseActivityHistory> userCourseActivityHistories) {
    this.userCourseActivityHistories = userCourseActivityHistories;
    return this;
  }

  public ExtendedUser addUserCourseActivityHistoriesItem(UserCourseActivityHistory userCourseActivityHistoriesItem) {
    if (this.userCourseActivityHistories == null) {
      this.userCourseActivityHistories = new ArrayList<UserCourseActivityHistory>();
    }
    this.userCourseActivityHistories.add(userCourseActivityHistoriesItem);
    return this;
  }

  /**
   * Get userCourseActivityHistories
   * @return userCourseActivityHistories
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<UserCourseActivityHistory> getUserCourseActivityHistories() {
    return userCourseActivityHistories;
  }

  public void setUserCourseActivityHistories(List<UserCourseActivityHistory> userCourseActivityHistories) {
    this.userCourseActivityHistories = userCourseActivityHistories;
  }

  public ExtendedUser userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExtendedUser extendedUser = (ExtendedUser) o;
    return Objects.equals(this.id, extendedUser.id) &&
        Objects.equals(this.permissionOnEntities, extendedUser.permissionOnEntities) &&
        Objects.equals(this.phone, extendedUser.phone) &&
        Objects.equals(this.userCourseActivityHistories, extendedUser.userCourseActivityHistories) &&
        Objects.equals(this.userId, extendedUser.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, permissionOnEntities, phone, userCourseActivityHistories, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExtendedUser {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    permissionOnEntities: ").append(toIndentedString(permissionOnEntities)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    userCourseActivityHistories: ").append(toIndentedString(userCourseActivityHistories)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

