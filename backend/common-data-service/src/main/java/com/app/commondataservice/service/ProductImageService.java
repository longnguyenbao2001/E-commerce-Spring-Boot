/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.ProductImageDTO;
import com.app.commondataservice.exception.DataNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
public interface ProductImageService {

    public void createProductImages(Long productId, List<MultipartFile> files)
            throws IOException;

    public List<ProductImageDTO> findProductImagesByProductId(Long productId)
            throws DataNotFoundException;
}
