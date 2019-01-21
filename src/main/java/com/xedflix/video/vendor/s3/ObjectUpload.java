package com.xedflix.video.vendor.s3;

/**
 * Author: Mohamed Saleem
 */
public interface ObjectUpload {
    public void upload(String objectKey, String filePath);
    public void remove(String objectKey);
}
