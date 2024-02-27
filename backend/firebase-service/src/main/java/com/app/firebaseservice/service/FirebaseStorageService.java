/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.firebaseservice.service;

import com.app.firebaseservice.dto.UploadFilesResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author user
 */
public interface FirebaseStorageService {

    public UploadFilesResponseDTO uploadImages(List<MultipartFile> files) throws IOException;
}
