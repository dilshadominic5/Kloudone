package com.xedflix.video.client.user_service_apiclient.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.xedflix.video.client.user_service_apiclient.ClientConfiguration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@FeignClient(name="${UserServiceAPIClient.name:UserServiceAPIClient}", url="${UserServiceAPIClient.url:localhost:8083/}", configuration = ClientConfiguration.class)
public interface OrganizationResourceApiClient extends OrganizationResourceApi {
}
