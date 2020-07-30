package com.romaomoura.cursospringmvc.services;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Value("${s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Cliente;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            LOG.info("Iniciando Upload...");
            s3Cliente.putObject(new PutObjectRequest(bucketName, "teste", file));
            LOG.info("Upload concluído!");
        } catch (AmazonServiceException err) {
            LOG.info("AmazonServiceException: " + err.getErrorMessage());
            LOG.info("Status do erro: " + err.getErrorCode());
        } catch (AmazonClientException err) {
            LOG.info("AmazonClientException: " + err.getMessage());
        }
    }
}