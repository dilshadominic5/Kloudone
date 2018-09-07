package com.xedflix.video.client.user_service_apiclient.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xedflix.video.client.user_service_apiclient.model.ExtendedUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Resource level permission. Much granular than an entity u1 | v1 | video u1 | l1 | livestream u1 | v2 | video
 */
@ApiModel(description = "Resource level permission. Much granular than an entity u1 | v1 | video u1 | l1 | livestream u1 | v2 | video")
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-09-07T19:33:07.937+05:30[Asia/Kolkata]")

public class EndUserResourceAccess   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("resourceId")
  private Integer resourceId = null;

  /**
   * Gets or Sets resourceType
   */
  public enum ResourceTypeEnum {
    VIDEO("VIDEO"),
    
    LIVE_STREAM("LIVE_STREAM"),
    
    BLOG_POST("BLOG_POST");

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

  @JsonProperty("user")
  private ExtendedUser user = null;

  public EndUserResourceAccess id(Long id) {
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

  public EndUserResourceAccess resourceId(Integer resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
  **/
  @ApiModelProperty(value = "")


  public Integer getResourceId() {
    return resourceId;
  }

  public void setResourceId(Integer resourceId) {
    this.resourceId = resourceId;
  }

  public EndUserResourceAccess resourceType(ResourceTypeEnum resourceType) {
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

  public EndUserResourceAccess user(ExtendedUser user) {
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
    EndUserResourceAccess endUserResourceAccess = (EndUserResourceAccess) o;
    return Objects.equals(this.id, endUserResourceAccess.id) &&
        Objects.equals(this.resourceId, endUserResourceAccess.resourceId) &&
        Objects.equals(this.resourceType, endUserResourceAccess.resourceType) &&
        Objects.equals(this.user, endUserResourceAccess.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, resourceId, resourceType, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EndUserResourceAccess {\n");
    
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

