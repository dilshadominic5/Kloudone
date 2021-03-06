package com.xedflix.video.config;

import com.amazonaws.auth.policy.actions.CloudFrontActions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.Cloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Properties specific to Xedflix Video Service.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = true)
public class ApplicationProperties {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }

    private final S3 s3 = new S3();

    public S3 getS3() {
        return s3;
    }

    private final Cloudfront cloudfront = new Cloudfront();

    public Cloudfront getCloudfront() {
        return cloudfront;
    }

    public static class S3 {

        private final UserVideoUploads userVideoUploads = new UserVideoUploads();

        public UserVideoUploads getUserVideoUploads() {
            return userVideoUploads;
        }

        public static class UserVideoUploads {

            private String bucket;

            private String region;

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

        }
    }

    public static class Cloudfront {

        private String baseUrl;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}
