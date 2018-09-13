package com.xedflix.video;

import com.xedflix.video.client.user_service_apiclient.api.RoleResourceApiClient;
import com.xedflix.video.client.user_service_apiclient.model.ActionPermissionForRole;
import com.xedflix.video.client.user_service_apiclient.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Profile("dev")
@Slf4j
@Configuration
public class AppDevConf {

    @Bean
    public RoleResourceApiClient clientController(){
        return new RoleResourceApiClient() {
            @Override
            public ResponseEntity<ActionPermissionForRole> getPermissionForRoleOnActionItemUsingGET(@NotNull @Valid String actionItem) {
                log.info("calling local bean in dev profile");
                return null;
            }

            @Override
            public ResponseEntity<UserRole> getRoleOfUserUsingGET() {
                log.info("calling local bean in dev profile");
                return null;
            }
        };
    }
}
