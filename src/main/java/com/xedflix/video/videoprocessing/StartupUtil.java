package com.xedflix.video.videoprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.File;

/**
 * Author: Mohamed Saleem
 */
@Configuration
public class StartupUtil {

    private final Logger logger = LoggerFactory.getLogger(StartupUtil.class);

    @Value("${application.video-processing.temp-dir}")
    private String tempDir;

    @EventListener(ApplicationReadyEvent.class)
    public void createTempDirectory() {
        String path = tempDir;
        File directory = new File(path);
        if(!directory.exists()) {
            logger.debug("Temp directory does not exist. Need to create.");
            if(!directory.mkdir()) {
                logger.error("Error creating temp directory");
            } else {
                logger.debug("Successfully created temp directory");
            }
        } else {
            logger.debug("Temp directory already exists");
        }
    }
}
