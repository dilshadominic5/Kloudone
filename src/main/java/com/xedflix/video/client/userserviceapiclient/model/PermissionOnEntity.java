package com.xedflix.video.client.userserviceapiclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity level permissions
 */
@ApiModel(description = "Entity level permissions")
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-09-07T19:33:07.937+05:30[Asia/Kolkata]")

public class PermissionOnEntity   {
  @JsonProperty("canCreate")
  private Boolean canCreate = null;

  @JsonProperty("canDelete")
  private Boolean canDelete = null;

  @JsonProperty("canRead")
  private Boolean canRead = null;

  @JsonProperty("canUpdate")
  private Boolean canUpdate = null;

  @JsonProperty("entityName")
  private String entityName = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("user")
  private ExtendedUser user = null;

  public PermissionOnEntity canCreate(Boolean canCreate) {
    this.canCreate = canCreate;
    return this;
  }

  /**
   * Get canCreate
   * @return canCreate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isCanCreate() {
    return canCreate;
  }

  public void setCanCreate(Boolean canCreate) {
    this.canCreate = canCreate;
  }

  public PermissionOnEntity canDelete(Boolean canDelete) {
    this.canDelete = canDelete;
    return this;
  }

  /**
   * Get canDelete
   * @return canDelete
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isCanDelete() {
    return canDelete;
  }

  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }

  public PermissionOnEntity canRead(Boolean canRead) {
    this.canRead = canRead;
    return this;
  }

  /**
   * Get canRead
   * @return canRead
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isCanRead() {
    return canRead;
  }

  public void setCanRead(Boolean canRead) {
    this.canRead = canRead;
  }

  public PermissionOnEntity canUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
    return this;
  }

  /**
   * Get canUpdate
   * @return canUpdate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isCanUpdate() {
    return canUpdate;
  }

  public void setCanUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
  }

  public PermissionOnEntity entityName(String entityName) {
    this.entityName = entityName;
    return this;
  }

  /**
   * Get entityName
   * @return entityName
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public PermissionOnEntity id(Long id) {
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

  public PermissionOnEntity user(ExtendedUser user) {
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
    PermissionOnEntity permissionOnEntity = (PermissionOnEntity) o;
    return Objects.equals(this.canCreate, permissionOnEntity.canCreate) &&
        Objects.equals(this.canDelete, permissionOnEntity.canDelete) &&
        Objects.equals(this.canRead, permissionOnEntity.canRead) &&
        Objects.equals(this.canUpdate, permissionOnEntity.canUpdate) &&
        Objects.equals(this.entityName, permissionOnEntity.entityName) &&
        Objects.equals(this.id, permissionOnEntity.id) &&
        Objects.equals(this.user, permissionOnEntity.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(canCreate, canDelete, canRead, canUpdate, entityName, id, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermissionOnEntity {\n");

    sb.append("    canCreate: ").append(toIndentedString(canCreate)).append("\n");
    sb.append("    canDelete: ").append(toIndentedString(canDelete)).append("\n");
    sb.append("    canRead: ").append(toIndentedString(canRead)).append("\n");
    sb.append("    canUpdate: ").append(toIndentedString(canUpdate)).append("\n");
    sb.append("    entityName: ").append(toIndentedString(entityName)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

