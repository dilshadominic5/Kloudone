package com.xedflix.video.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Xedflix Video Service.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final S3 s3 = new S3();

    public S3 getS3() {
        return s3;
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
}
