package com.xedflix.video.client.user_service_apiclient.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.xedflix.video.client.user_service_apiclient.ClientConfiguration;

@FeignClient(name="${UserServiceAPIClient.name:UserServiceAPIClient}", url="${UserServiceAPIClient.url:localhost:8083/}", configuration = ClientConfiguration.class)
public interface PermissionOnEntityResourceApiClient extends PermissionOnEntityResourceApi {
}