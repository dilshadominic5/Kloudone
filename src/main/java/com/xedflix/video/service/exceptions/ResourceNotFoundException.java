package com.xedflix.video.service.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("Resource Not Found");
    }
}
