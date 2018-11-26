package com.xedflix.video.service.exceptions;

public class ResponseErrorException extends Exception {

    public ResponseErrorException() {
        super("Error retrieving live streams");
    }

    public ResponseErrorException(String message) {
        super(message);
    }
}
