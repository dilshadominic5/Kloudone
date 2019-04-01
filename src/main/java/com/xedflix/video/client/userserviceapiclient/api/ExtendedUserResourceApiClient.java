package com.xedflix.video.client.userserviceapiclient.api;

import com.xedflix.video.client.userserviceapiclient.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@FeignClient(name="${UserServiceAPIClient.name:UserServiceAPIClient}", url="${UserServiceAPIClient.url:localhost:8083/}", configuration = ClientConfiguration.class)
public interface ExtendedUserResourceApiClient extends ExtendedUserResourceApi {
}
