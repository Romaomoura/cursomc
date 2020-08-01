package com.romaomoura.cursospringmvc.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.romaomoura.cursospringmvc.services.exceptions.FileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Value("${s3.bucket}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Cliente;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream is = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(is, fileName, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType) {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            LOG.info("Iniciando upload");
            s3Cliente.putObject(bucketName, fileName, is, meta);
            LOG.info("Upload finalizado");
            return s3Cliente.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }

}