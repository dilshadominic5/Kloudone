package com.xedflix.video.videoprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Author: Mohamed Saleem
 */
public class FFMpeg {
    public static final String FFMpegLocation = "/usr/bin/ffmpeg";
    public static final String tempFilePath = "/tmp/videoservice/";
    public static final String yesFlag = "-y";

    private static List<String> defaultCommandList = new ArrayList<>(
        Arrays.asList("-vframes", "1", "-q:v", "2")
    );

    private String ffmpegPath;
    private String fileName;

    private List<String> offsetList = new ArrayList<>(Collections.singletonList("-ss"));
    private List<String> inputPathList = new ArrayList<>(Collections.singletonList("-i"));

    private List<String> commandList = new ArrayList<>();

    public void setFileName(String fileName) {
        this.fileName = tempFilePath + fileName;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public void setOffset(Integer offset) {
        this.offsetList.add(offset.toString());
    }

    public void setInputPath(String path) {
        this.inputPathList.add(path);
    }

    public void generateThumbnail() throws IOException, InterruptedException {
        //Example: ffmpeg -ss 5 -i input -vframes 1 -q:v 2 output.jpg
        execute();
    }

    private void execute() throws IOException, InterruptedException {
        this.commandList.add(this.ffmpegPath);
        this.commandList.add(yesFlag);
        this.commandList.addAll(this.offsetList);
        this.commandList.addAll(this.inputPathList);
        this.commandList.addAll(defaultCommandList);
        this.commandList.add(this.fileName);

        ProcessBuilder processBuilder = new ProcessBuilder(this.commandList);
        Process process = processBuilder.start();
        process.waitFor();
    }
}
