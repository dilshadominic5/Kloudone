package com.xedflix.video.client.user_service_apiclient.api;

import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserRole;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import com.xedflix.video.client.user_service_apiclient.ClientConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Profile("prod")
@FeignClient(
    name="${UserServiceAPIClient.name:UserServiceAPIClient}",
    url="${application.services.external.user-service.base-url}",
    configuration = ClientConfiguration.class,
    fallbackFactory = RoleResourceApiClient.RoleResourceApiClientFallback.class
)
public interface RoleResourceApiClient extends RoleResourceApi {
    @Component
    class RoleResourceApiClientFallback implements FallbackFactory<RoleResourceApiClient> {

        @Override
        public RoleResourceApiClient create(Throwable throwable) {
            return new RoleResourceApiClient() {
                @Override
                public ResponseEntity<ActionPermissionForRole> getPermissionForRoleOnActionItemUsingGET(@NotNull @Valid String actionItem) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                @Override
                public ResponseEntity<UserRole> getRoleOfUserUsingGET() {
                    return null;
                }
            };
        }
    }
}
