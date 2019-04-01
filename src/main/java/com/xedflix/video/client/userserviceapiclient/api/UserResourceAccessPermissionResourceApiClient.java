package com.xedflix.video.client.userserviceapiclient.api;

import com.xedflix.video.client.userserviceapiclient.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@FeignClient(name="${UserServiceAPIClient.name:UserServiceAPIClient}", url="${application.services.external.user-service.base-url}", configuration = ClientConfiguration.class)
public interface UserResourceAccessPermissionResourceApiClient extends UserResourceAccessPermissionResourceApi {
}
