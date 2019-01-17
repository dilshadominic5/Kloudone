package com.xedflix.video.videoprocessing;

import com.xedflix.video.exceptions.OutputEmptyException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Mohamed Saleem
 */
public class FFProbe {

    public static final String FFProbeLocation = "/usr/bin/ffprobe";

    private String inputPath;
    private String ffProbePath;

    private static List<String> defaultProbeCommandList = new ArrayList<>(
        Arrays.asList("-v", "quiet", "-print_format", "json" ,"-show_format", "-show_streams")
    );

    private List<String> commandList = new ArrayList<>();

    public void setFFProbePath(String path) {
        this.ffProbePath = path;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public long getVideoLength() throws IOException, InterruptedException, JSONException, OutputEmptyException {
        String output = probe();
        if(output.isEmpty()) {
            throw new OutputEmptyException();
        }
        JSONObject jsonObject = new JSONObject(output);
        JSONObject format = jsonObject.getJSONObject("format");
        String duration = format.getString("duration");
        Double value = Double.parseDouble(duration);
        return value.longValue();
    }

    private String probe() throws IOException, InterruptedException {
        this.commandList.add(this.ffProbePath);
        this.commandList.addAll(defaultProbeCommandList);
        this.commandList.add(this.inputPath);
        ProcessBuilder processBuilder = new ProcessBuilder(this.commandList);
        Process process = processBuilder.start();
        IOThreadHandler ioThreadHandler = new IOThreadHandler(
            process.getInputStream()
        );
        ioThreadHandler.start();
        process.waitFor();
        return ioThreadHandler.getOutput();
    }

}

