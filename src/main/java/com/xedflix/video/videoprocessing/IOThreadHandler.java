package com.xedflix.video.videoprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Mohamed Saleem
 */
public class IOThreadHandler extends Thread {

    private Logger log = LoggerFactory.getLogger(IOThreadHandler.class);

    private InputStream inputStream;
    private List<String> outputList;
    IOThreadHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            outputList = new ArrayList<>();
            String s;

            while ((s = bufferedReader.readLine()) != null) {
                outputList.add(s.trim());
            }
        } catch (IOException e) {
            log.error("IOHandler, IOException {}", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("IOHandler, IOException: {}", e);
                }
            }
        }
    }

    String getOutput() {
        return outputList.stream().map(Object::toString).collect(Collectors.joining(""));
    }
}
