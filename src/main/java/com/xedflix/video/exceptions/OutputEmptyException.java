package com.xedflix.video.exceptions;

/**
 * Author: Mohamed Saleem
 */
public class OutputEmptyException extends Exception {
    public OutputEmptyException() {
        super("Output Empty");
    }

    public OutputEmptyException(String message) {
        super(message);
    }
}
