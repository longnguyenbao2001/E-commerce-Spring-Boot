/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.ProductImageRepository;
import com.app.commondataservice.dto.ProductImageDTO;
import com.app.commondataservice.dto.UploadFilesResponseDTO;
import com.app.commondataservice.entity.ProductImages;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.service.ProductService;
import com.app.commondataservice.exception.DataNotFoundException;
import com.app.commondataservice.service.CallApiService;
import com.app.commondataservice.service.ProductImageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CallApiService callApiService;

    @Autowired
    private DTOConverter dtoConverter;

    @Override
    public void createProductImages(Long productId, List<MultipartFile> files) throws IOException {
        UploadFilesResponseDTO uploadFilesResponseDTO = callApiService.uploadImages(files);

        Products product = new Products(productId);
        ProductImages productImage;

        for (String url : uploadFilesResponseDTO.getDownloadUrls()) {
            productImage = new ProductImages();
            productImage.setProducts(product);
            productImage.setUrl(url);

            productImageRepository.save(productImage);
        }
    }

    @Override
    public List<ProductImageDTO> findProductImagesByProductId(Long productId) throws DataNotFoundException {
        Products product = productService.findProductByProductId(productId);
        List<ProductImages> listProductImages = product.getProductImagesList();
        if (listProductImages.isEmpty()) {
            throw new DataNotFoundException();
        }

        List<ProductImageDTO> res = new ArrayList<>();
        for (ProductImages productImages : listProductImages) {
            res.add(dtoConverter.convertProductImageToDTO(productImages));
        }

        return res;
    }
}
