package com.xedflix.video;

import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.api.UserResourceAccessPermissionResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserResourceAccessPermission;
import com.xedflix.video.client.user_service_apiclient.model.UserResourceAccessPermissionDTO;
import com.xedflix.video.client.user_service_apiclient.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Profile({"dev", "test"})
@Slf4j
@Configuration
public class AppDevConf {

    @Bean
    public RoleResourceApiClient clientController(){
        return new RoleResourceApiClient() {
            @Override
            public ResponseEntity<ActionPermissionForRole> getPermissionForRoleOnActionItemUsingGET(@NotNull @Valid String actionItem) {
//                log.info("calling local bean in dev profile");

                ActionPermissionForRole actionPermissionForRole = new ActionPermissionForRole();
                actionPermissionForRole.setCanCreate(true);
                actionPermissionForRole.setCanRead(true);
                actionPermissionForRole.setCanDelete(true);
                actionPermissionForRole.setCanUpdate(true);

                return ResponseEntity.ok(actionPermissionForRole);
            }

            @Override
            public ResponseEntity<UserRole> getRoleOfUserUsingGET() {
//                log.info("calling local bean in dev profile");
                return null;
            }
        };
    }

    @Bean
    public UserResourceAccessPermissionResourceApiClient userResourceAccessPermissionResourceApiClientController() {
        return new UserResourceAccessPermissionResourceApiClient() {
            @Override
            public ResponseEntity<List<UserResourceAccessPermission>> createMultipleUserResourceAccessPermissionUsingPOST(@Valid List<UserResourceAccessPermission> userResourceAccessPermission) {
                return null;
            }

            @Override
            public ResponseEntity<UserResourceAccessPermission> createUserResourceAccessPermissionUsingPOST(@Valid UserResourceAccessPermission userResourceAccessPermission) {
                return null;
            }

            @Override
            public ResponseEntity<Void> deleteUserResourceAccessPermissionUsingDELETE(Long id) {
                return null;
            }

            @Override
            public ResponseEntity<List<UserResourceAccessPermission>> findByResourceIdsForUserUsingGET(@NotNull @Valid List<Long> resourceIds, @NotNull @Valid String resourceType) {
                return null;
            }

            @Override
            public ResponseEntity<List<UserResourceAccessPermission>> findResourcesUsingGET(@NotNull @Valid String resourceType, @Valid Long offset, @Valid Integer page, @Valid Integer pageNumber, @Valid Integer pageSize, @Valid Boolean paged, @Valid Integer size, @Valid List<String> sort, @Valid Boolean sortSorted, @Valid Boolean sortUnsorted, @Valid Boolean unpaged) {
                List<UserResourceAccessPermission> userResourceAccessPermissions = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    UserResourceAccessPermission userResourceAccessPermission = new UserResourceAccessPermission();
                    userResourceAccessPermission.setId(((Integer) i).longValue());
                    userResourceAccessPermission.setResourceId(((Integer)(i)).longValue());
                    userResourceAccessPermissions.add(userResourceAccessPermission);
                }
                return ResponseEntity.ok(userResourceAccessPermissions);
            }

            @Override
            public ResponseEntity<List<UserResourceAccessPermissionDTO>> findUsersAssignedForResourceUsingGET(@NotNull @Valid Long resourceId, @NotNull @Valid String resourceType) {
                return null;
            }

            @Override
            public ResponseEntity<List<UserResourceAccessPermission>> getAllUserResourceAccessPermissionsUsingGET(@Valid OffsetDateTime createdAtEquals, @Valid OffsetDateTime createdAtGreaterOrEqualThan, @Valid OffsetDateTime createdAtGreaterThan, @Valid Integer createdAtIn0DayOfMonth, @Valid String createdAtIn0DayOfWeek, @Valid Integer createdAtIn0DayOfYear, @Valid Integer createdAtIn0Hour, @Valid Integer createdAtIn0Minute, @Valid String createdAtIn0Month, @Valid Integer createdAtIn0MonthValue, @Valid Integer createdAtIn0Nano, @Valid String createdAtIn0OffsetId, @Valid Boolean createdAtIn0OffsetRulesFixedOffset, @Valid Integer createdAtIn0OffsetRulesTransitionRules0DayOfMonthIndicator, @Valid String createdAtIn0OffsetRulesTransitionRules0DayOfWeek, @Valid Integer createdAtIn0OffsetRulesTransitionRules0LocalTimeHour, @Valid Integer createdAtIn0OffsetRulesTransitionRules0LocalTimeMinute, @Valid Integer createdAtIn0OffsetRulesTransitionRules0LocalTimeNano, @Valid Integer createdAtIn0OffsetRulesTransitionRules0LocalTimeSecond, @Valid Boolean createdAtIn0OffsetRulesTransitionRules0MidnightEndOfDay, @Valid String createdAtIn0OffsetRulesTransitionRules0Month, @Valid String createdAtIn0OffsetRulesTransitionRules0TimeDefinition, @Valid OffsetDateTime createdAtIn0OffsetRulesTransitions0DateTimeAfter, @Valid OffsetDateTime createdAtIn0OffsetRulesTransitions0DateTimeBefore, @Valid Integer createdAtIn0OffsetRulesTransitions0DurationNano, @Valid Boolean createdAtIn0OffsetRulesTransitions0DurationNegative, @Valid Long createdAtIn0OffsetRulesTransitions0DurationSeconds, @Valid Boolean createdAtIn0OffsetRulesTransitions0DurationUnits0DateBased, @Valid Boolean createdAtIn0OffsetRulesTransitions0DurationUnits0DurationEstimated, @Valid Boolean createdAtIn0OffsetRulesTransitions0DurationUnits0TimeBased, @Valid Boolean createdAtIn0OffsetRulesTransitions0DurationZero, @Valid Boolean createdAtIn0OffsetRulesTransitions0Gap, @Valid OffsetDateTime createdAtIn0OffsetRulesTransitions0Instant, @Valid Boolean createdAtIn0OffsetRulesTransitions0Overlap, @Valid Integer createdAtIn0OffsetTotalSeconds, @Valid Integer createdAtIn0Second, @Valid Integer createdAtIn0Year, @Valid String createdAtIn0ZoneId, @Valid Boolean createdAtIn0ZoneRulesFixedOffset, @Valid Integer createdAtIn0ZoneRulesTransitionRules0DayOfMonthIndicator, @Valid String createdAtIn0ZoneRulesTransitionRules0DayOfWeek, @Valid Integer createdAtIn0ZoneRulesTransitionRules0LocalTimeHour, @Valid Integer createdAtIn0ZoneRulesTransitionRules0LocalTimeMinute, @Valid Integer createdAtIn0ZoneRulesTransitionRules0LocalTimeNano, @Valid Integer createdAtIn0ZoneRulesTransitionRules0LocalTimeSecond, @Valid Boolean createdAtIn0ZoneRulesTransitionRules0MidnightEndOfDay, @Valid String createdAtIn0ZoneRulesTransitionRules0Month, @Valid String createdAtIn0ZoneRulesTransitionRules0OffsetAfterId, @Valid Integer createdAtIn0ZoneRulesTransitionRules0OffsetAfterTotalSeconds, @Valid String createdAtIn0ZoneRulesTransitionRules0OffsetBeforeId, @Valid Integer createdAtIn0ZoneRulesTransitionRules0OffsetBeforeTotalSeconds, @Valid String createdAtIn0ZoneRulesTransitionRules0StandardOffsetId, @Valid Integer createdAtIn0ZoneRulesTransitionRules0StandardOffsetTotalSeconds, @Valid String createdAtIn0ZoneRulesTransitionRules0TimeDefinition, @Valid OffsetDateTime createdAtIn0ZoneRulesTransitions0DateTimeAfter, @Valid OffsetDateTime createdAtIn0ZoneRulesTransitions0DateTimeBefore, @Valid Integer createdAtIn0ZoneRulesTransitions0DurationNano, @Valid Boolean createdAtIn0ZoneRulesTransitions0DurationNegative, @Valid Long createdAtIn0ZoneRulesTransitions0DurationSeconds, @Valid Boolean createdAtIn0ZoneRulesTransitions0DurationUnits0DateBased, @Valid Boolean createdAtIn0ZoneRulesTransitions0DurationUnits0DurationEstimated, @Valid Boolean createdAtIn0ZoneRulesTransitions0DurationUnits0TimeBased, @Valid Boolean createdAtIn0ZoneRulesTransitions0DurationZero, @Valid Boolean createdAtIn0ZoneRulesTransitions0Gap, @Valid OffsetDateTime createdAtIn0ZoneRulesTransitions0Instant, @Valid String createdAtIn0ZoneRulesTransitions0OffsetAfterId, @Valid Integer createdAtIn0ZoneRulesTransitions0OffsetAfterTotalSeconds, @Valid String createdAtIn0ZoneRulesTransitions0OffsetBeforeId, @Valid Integer createdAtIn0ZoneRulesTransitions0OffsetBeforeTotalSeconds, @Valid Boolean createdAtIn0ZoneRulesTransitions0Overlap, @Valid OffsetDateTime createdAtLessOrEqualThan, @Valid OffsetDateTime createdAtLessThan, @Valid Boolean createdAtSpecified, @Valid Long idEquals, @Valid Long idGreaterOrEqualThan, @Valid Long idGreaterThan, @Valid List<Long> idIn, @Valid Long idLessOrEqualThan, @Valid Long idLessThan, @Valid Boolean idSpecified, @Valid Long offset, @Valid Integer page, @Valid Integer pageNumber, @Valid Integer pageSize, @Valid Boolean paged, @Valid Long permissionLastGivenByEquals, @Valid Long permissionLastGivenByGreaterOrEqualThan, @Valid Long permissionLastGivenByGreaterThan, @Valid List<Long> permissionLastGivenByIn, @Valid Long permissionLastGivenByLessOrEqualThan, @Valid Long permissionLastGivenByLessThan, @Valid Boolean permissionLastGivenBySpecified, @Valid Long resourceIdEquals, @Valid Long resourceIdGreaterOrEqualThan, @Valid Long resourceIdGreaterThan, @Valid List<Long> resourceIdIn, @Valid Long resourceIdLessOrEqualThan, @Valid Long resourceIdLessThan, @Valid Boolean resourceIdSpecified, @Valid String resourceTypeEquals, @Valid List<String> resourceTypeIn, @Valid Boolean resourceTypeSpecified, @Valid Integer size, @Valid List<String> sort, @Valid Boolean sortSorted, @Valid Boolean sortUnsorted, @Valid Boolean unpaged, @Valid Long userIdEquals, @Valid Long userIdGreaterOrEqualThan, @Valid Long userIdGreaterThan, @Valid List<Long> userIdIn, @Valid Long userIdLessOrEqualThan, @Valid Long userIdLessThan, @Valid Boolean userIdSpecified) {
                return null;
            }

            @Override
            public ResponseEntity<UserResourceAccessPermission> getUserResourceAccessPermissionUsingGET(Long id) {
                return null;
            }

            @Override
            public ResponseEntity<String> hasAccessUsingGET(@NotNull @Valid Long resourceId, @NotNull @Valid String resourceType) {
                return ResponseEntity.ok("");
            }

            @Override
            public ResponseEntity<UserResourceAccessPermission> updateUserResourceAccessPermissionUsingPUT(@Valid UserResourceAccessPermission userResourceAccessPermission) {
                return null;
            }
        };
    }
}
