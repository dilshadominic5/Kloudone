package com.xedflix.video.client.user_service_apiclient.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ActionPermissionForRole
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2018-09-07T19:33:07.937+05:30[Asia/Kolkata]")

public class ActionPermissionForRole   {
  @JsonProperty("canCreate")
  private Boolean canCreate = null;

  @JsonProperty("canDelete")
  private Boolean canDelete = null;

  @JsonProperty("canRead")
  private Boolean canRead = null;

  @JsonProperty("canUpdate")
  private Boolean canUpdate = null;

  public ActionPermissionForRole canCreate(Boolean canCreate) {
    this.canCreate = canCreate;
    return this;
  }

  /**
   * Get canCreate
   * @return canCreate
  **/
  @ApiModelProperty(value = "")


  public Boolean isCanCreate() {
    return canCreate;
  }

  public void setCanCreate(Boolean canCreate) {
    this.canCreate = canCreate;
  }

  public ActionPermissionForRole canDelete(Boolean canDelete) {
    this.canDelete = canDelete;
    return this;
  }

  /**
   * Get canDelete
   * @return canDelete
  **/
  @ApiModelProperty(value = "")


  public Boolean isCanDelete() {
    return canDelete;
  }

  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }

  public ActionPermissionForRole canRead(Boolean canRead) {
    this.canRead = canRead;
    return this;
  }

  /**
   * Get canRead
   * @return canRead
  **/
  @ApiModelProperty(value = "")


  public Boolean isCanRead() {
    return canRead;
  }

  public void setCanRead(Boolean canRead) {
    this.canRead = canRead;
  }

  public ActionPermissionForRole canUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
    return this;
  }

  /**
   * Get canUpdate
   * @return canUpdate
  **/
  @ApiModelProperty(value = "")


  public Boolean isCanUpdate() {
    return canUpdate;
  }

  public void setCanUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActionPermissionForRole actionPermissionForRole = (ActionPermissionForRole) o;
    return Objects.equals(this.canCreate, actionPermissionForRole.canCreate) &&
        Objects.equals(this.canDelete, actionPermissionForRole.canDelete) &&
        Objects.equals(this.canRead, actionPermissionForRole.canRead) &&
        Objects.equals(this.canUpdate, actionPermissionForRole.canUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(canCreate, canDelete, canRead, canUpdate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActionPermissionForRole {\n");
    
    sb.append("    canCreate: ").append(toIndentedString(canCreate)).append("\n");
    sb.append("    canDelete: ").append(toIndentedString(canDelete)).append("\n");
    sb.append("    canRead: ").append(toIndentedString(canRead)).append("\n");
    sb.append("    canUpdate: ").append(toIndentedString(canUpdate)).append("\n");
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

