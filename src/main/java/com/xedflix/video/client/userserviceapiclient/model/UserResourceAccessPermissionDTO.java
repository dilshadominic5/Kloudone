package com.xedflix.video.client.userserviceapiclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * UserResourceAccessPermissionDTO
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-10-15T18:42:14.703+05:30[Asia/Kolkata]")

public class UserResourceAccessPermissionDTO   {
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

  @JsonProperty("userDTO")
  private UserDTO userDTO = null;

  public UserResourceAccessPermissionDTO id(Long id) {
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

  public UserResourceAccessPermissionDTO permissionLastGivenBy(Long permissionLastGivenBy) {
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

  public UserResourceAccessPermissionDTO resourceId(Long resourceId) {
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

  public UserResourceAccessPermissionDTO resourceType(ResourceTypeEnum resourceType) {
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

  public UserResourceAccessPermissionDTO userDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
    return this;
  }

  /**
   * Get userDTO
   * @return userDTO
  **/
  @ApiModelProperty(value = "")

  @Valid

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserResourceAccessPermissionDTO userResourceAccessPermissionDTO = (UserResourceAccessPermissionDTO) o;
    return Objects.equals(this.id, userResourceAccessPermissionDTO.id) &&
        Objects.equals(this.permissionLastGivenBy, userResourceAccessPermissionDTO.permissionLastGivenBy) &&
        Objects.equals(this.resourceId, userResourceAccessPermissionDTO.resourceId) &&
        Objects.equals(this.resourceType, userResourceAccessPermissionDTO.resourceType) &&
        Objects.equals(this.userDTO, userResourceAccessPermissionDTO.userDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, permissionLastGivenBy, resourceId, resourceType, userDTO);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserResourceAccessPermissionDTO {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    permissionLastGivenBy: ").append(toIndentedString(permissionLastGivenBy)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    resourceType: ").append(toIndentedString(resourceType)).append("\n");
    sb.append("    userDTO: ").append(toIndentedString(userDTO)).append("\n");
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

