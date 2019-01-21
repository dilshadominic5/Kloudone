package com.xedflix.video.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.xedflix.video.domain.Video;
import com.xedflix.video.exceptions.OutputEmptyException;
import com.xedflix.video.repository.VideoRepository;
import com.xedflix.video.service.exceptions.ResourceNotFoundException;
import com.xedflix.video.vendor.s3.impl.ImageUpload;
import com.xedflix.video.videoprocessing.FFMpeg;
import com.xedflix.video.videoprocessing.FFProbe;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Author: Mohamed Saleem
 */
@Service
public class VideoProcessorService {

    private final Logger logger = LoggerFactory.getLogger(VideoProcessorService.class);

    @Value("${application.video-processing.ffprobe.path}")
    private String ffProbePath;

    @Value("${application.video-processing.ffmpeg.path}")
    private String ffMpegPath;

    @Value("${application.vendor.s3.kloudlearn-file-uploads.distribution.root}")
    private String cloudFrontUrl;

    @Autowired
    private VideoRepository videoRepository;

    @KafkaListener(
        topics = "${application.kafka.video-processing.topic}",
        groupId = "${application.kafka.video-processing.group-id}"
    )
    public void process(String message) {
        logger.debug("Video ID: {}", message);

        try {

            Long id = Long.valueOf(message);

            Optional<Video> videoOptional = videoRepository.findById(id);
            if(videoOptional.isPresent()) {
                Video video = videoOptional.get();
                String url = video.getUrl();

                // Get video duration
                FFProbe ffProbe = new FFProbe();
                ffProbe.setFFProbePath(ffProbePath);
                ffProbe.setInputPath(url);

                Long durationInLong = ffProbe.getVideoLength();
                video.setDuration(durationInLong.floatValue());

                // Update the video duration
                video = videoRepository.save(video);

                logger.debug("Successfully updated video duration: {} {}", message, durationInLong);

                // Get video thumbnail
                Long offsetToGenerateImageAt = durationInLong / 2L;

                logger.debug("About to create thumbnail for {} at {}", message, offsetToGenerateImageAt);
                FFMpeg ffMpeg = new FFMpeg();
                ffMpeg.setFfmpegPath(ffMpegPath);
                ffMpeg.setInputPath(url);
                ffMpeg.setOffset(offsetToGenerateImageAt.intValue());

                String thumbnailName = video.getFileName().split("\\.")[0] + ".jpg";
                ffMpeg.setFileName(thumbnailName);

                ffMpeg.generateThumbnail();
                logger.debug("Thumbnail generation successful for {}", message);

                logger.debug("Uploading to S3: {}", message);
                // Upload to S3

                String path = FFMpeg.tempFilePath + thumbnailName;

                uploadTImageToS3(thumbnailName, path);

                logger.debug("Upload to S3 successful: {}", message);

                String cfUrl = cloudFrontUrl + "/" + thumbnailName;

                video.setImageUrl(cfUrl);
                video = videoRepository.save(video);

                logger.debug("Video processing complete for: {} - Thumbnail: {}, Duration: {}",
                    message, video.getImageUrl(), video.getDuration());

            } else {
                throw new ResourceNotFoundException();
            }

        } catch (ResourceNotFoundException | IOException | OutputEmptyException e) {
            logger.error("Error processing message: {}", message, e);
        } catch (SdkClientException e) {
            logger.error("Error uploading image to S3: {}", message, e);
        } catch (Exception e) {
            logger.error("Unknown Exception: {}", message, e);
        }
    }

    /**
     * Upload the generated image to S3
     */
    private void uploadTImageToS3(String fileName, String filePath) {
        ImageUpload imageUpload = new ImageUpload();
        imageUpload.upload(fileName, filePath);
    }


}
