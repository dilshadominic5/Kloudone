package com.xedflix.video.service;

import com.xedflix.video.exceptions.OutputEmptyException;
import com.xedflix.video.repository.VideoRepository;
import com.xedflix.video.videoprocessing.FFProbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author: Mohamed Saleem
 */
@Service
public class VideoProcessorService {

    @Value("${application.video-processing.ffprobe.path}")
    private String ffProbePath;

    @Autowired
    private VideoRepository videoRepository;

    @Async
    public void updateVideoMetaData(final Long videoId, final String videoUrl) {

    }

    public Long getVideoLength(final String videoUrl) throws InterruptedException, OutputEmptyException, IOException {
        FFProbe ffProbe = new FFProbe();
        ffProbe.setFFProbePath(ffProbePath);

        return ffProbe.getVideoLength();
    }

}
