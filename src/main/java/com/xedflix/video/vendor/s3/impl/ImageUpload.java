package com.xedflix.video.vendor.s3.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.xedflix.video.vendor.s3.ObjectUpload;

import java.io.File;

/**
 * Author: Mohamed Saleem
 */
public class ImageUpload implements ObjectUpload {

    private static final String BUCKET_NAME = "kloudlearn-file-uploads";

    @Override
    public void upload(String uploadedFileName, String filePath) throws AmazonServiceException, SdkClientException {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .withCredentials(new ProfileCredentialsProvider())
            .build();

        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, uploadedFileName, new File(filePath));
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        request.setMetadata(metadata);
        s3Client.putObject(request);
    }

    @Override
    public void remove(String objectKey) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .withCredentials(new ProfileCredentialsProvider())
            .build();
        DeleteObjectRequest request = new DeleteObjectRequest(BUCKET_NAME, objectKey);
        s3Client.deleteObject(request);
    }
}
