/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.firebaseservice.service.impl;

import com.app.firebaseservice.dto.UploadFilesResponseDTO;
import com.app.firebaseservice.service.FirebaseStorageService;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Autowired
    private Environment env;

    @Autowired
    private FirebaseApp firebaseApp;

    @Override
    public UploadFilesResponseDTO uploadImages(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IOException();
        }

        UploadFilesResponseDTO downloadUrls = new UploadFilesResponseDTO();

        Bucket bucket = StorageClient
                .getInstance(firebaseApp)
                .bucket(env.getProperty("firebase.app.storage.blobId"));
        Storage storage = bucket.getStorage();

        for (MultipartFile file : files) {
            String imageName = generateFileName(file.getOriginalFilename());

            BlobId blobId = BlobId.of(bucket.getName(), "product_images/" + imageName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                    .build();

            Blob blob = storage.create(blobInfo, file.getInputStream());

//        storage.delete(bucket.getName(), "product_images/" + imageName);
            String downloadUrl = String.format("%s/%s/%s",
                    env.getProperty("firebase.domain"),
                    blobId.getBucket(),
                    blobId.getName());

            downloadUrls.getDownloadUrls().add(downloadUrl);
        }

        return downloadUrls;
    }

    private String generateFileName(String originalFileName) {
        return String.format("%s.%s",
                UUID.randomUUID().toString(),
                StringUtils.getFilenameExtension(originalFileName));
    }
}
