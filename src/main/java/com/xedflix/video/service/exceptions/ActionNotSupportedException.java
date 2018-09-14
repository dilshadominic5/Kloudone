package com.xedflix.video.service.exceptions;

public class ActionNotSupportedException extends Exception {

    public ActionNotSupportedException() {
        super("This action is not supported as you do not have enough permissions.");
    }

    public ActionNotSupportedException(String message) {
        super(message);
    }
}
