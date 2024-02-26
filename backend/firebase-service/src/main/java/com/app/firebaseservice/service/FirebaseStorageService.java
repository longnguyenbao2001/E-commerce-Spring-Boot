/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.firebaseservice.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 *
 * @author user
 */
public interface FirebaseStorageService {

    public String uploadProductImage(MultipartFile file) throws IOException;
}
