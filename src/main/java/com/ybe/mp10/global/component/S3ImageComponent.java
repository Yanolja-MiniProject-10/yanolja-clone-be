package com.ybe.mp10.global.component;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3ImageComponent {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(String type, Long id, MultipartFile file) {
        String uploadFileName = type + "_" + id+"_"+ file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {

            amazonS3.putObject(bucket, uploadFileName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception during uploading file to S3");
        }
        return amazonS3.getUrl(bucket, uploadFileName).toString();
    }

    public List<String> uploadImages(String type, Long id, List<MultipartFile> multipartFiles) {
        List<String> createdFileNames = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            createdFileNames.add(uploadImage(type, id, file));
        }
        return createdFileNames;
    }
}
