package com.xedflix.video.vendor.s3;

import com.xedflix.video.vendor.s3.impl.ImageUpload;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

/**
 * Author: Mohamed Saleem
 */
public class ImageUploadTest {
    @Test
    public void testImageUpload() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("static/test.jpg");

        Assert.assertNotEquals(url, null);

        String key = "test-image-upload.jpg";
        ImageUpload imageUpload = new ImageUpload();
        imageUpload.upload(key, url.getPath());
        imageUpload.remove(key);
    }
}
