/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.ProductRepository;
import com.app.commondataservice.dto.CreateProductRequestDTO;
import com.app.commondataservice.dto.ProductDTO;
import com.app.commondataservice.dto.ProductDetailDTO;
import com.app.commondataservice.entity.Categories;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.ProductImages;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.entity.Variants;
import com.app.commondataservice.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import com.app.commondataservice.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.ListProductDTO;
import com.app.commondataservice.dto.PutProductRequestDTO;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author user
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private Environment env;

    @Override
    public Products findProductByProductId(Long productId)
            throws DataNotFoundException {
        Optional<Products> opProduct = productRepository.findById(productId);
        if (!opProduct.isPresent()) {
            throw new DataNotFoundException();
        }

        return opProduct.get();
    }

    @Override
    public ListProductDTO getListProduct(
            String keyword, List<Long> categoryIds,
            Integer page, Integer pageSize, List<String> orderBy, String orderDirection) {
        ListProductDTO res = new ListProductDTO();

        Sort.Direction direction;
        if (orderDirection.equals(env.getProperty("pagination.sort.direction.desc"))) {
            direction = Sort.Direction.DESC;
        } else {
            direction = Sort.Direction.ASC;
        }

        List<Sort.Order> sortOrders = new ArrayList<>();
        for (String column : orderBy) {
            sortOrders.add(new Sort.Order(direction, column));
        }
        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        List<Products> listProducts;
        if (categoryIds != null) {
            res.setTotalCount(productRepository.countByNameContainingAndCategoriesList_IdIn(keyword, categoryIds));
//            listProducts = productRepository.findDistinctByNameContainingAndCategoriesList_IdInOrCategoriesList_Categories_IdIn(keyword, categoryIds, categoryIds, pageable);
            listProducts = productRepository.findByNameContainingAndCategoriesList_IdIn(keyword, categoryIds, pageable);
        } else {
            res.setTotalCount(productRepository.countByNameContaining(keyword));
            listProducts = productRepository.findByNameContaining(keyword, pageable);
        }

        for (Products product : listProducts) {
            ProductDTO productDTO = dtoConverter.convertProductToDTO(product);

            for (ProductImages pm : product.getProductImagesList()) {
                productDTO.setImageUrl(pm.getUrl());
                break;
            }

            for (Variants v : product.getVariantsList()) {
                productDTO.setUnitPrice(v.getUnitPrice());
                break;
            }

            res.getListProducts().add(productDTO);
        }
        return res;
    }

    @Override
    public ProductDetailDTO getProductDetail(Long productId) throws DataNotFoundException {
        return dtoConverter.convertProductDetailToDTO(this.findProductByProductId(productId));
    }

    @Override
    public void createProduct(CreateProductRequestDTO createProductRequestDTO, Long refUserId) {
        Products product = new Products();
        product.setName(createProductRequestDTO.getName());
        product.setDescription(createProductRequestDTO.getDescription());

        Users user = new Users(refUserId);
        product.setUsers(user);

        Categories category = new Categories(createProductRequestDTO.getCategoryId());
//        product.setCategories(category);

        productRepository.save(product);

    }

    @Override
    public void putProduct(PutProductRequestDTO putProductRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.findProductByProductId(putProductRequestDTO.getId());

        product.setName(putProductRequestDTO.getName());
        product.setDescription(putProductRequestDTO.getDescription());

        Categories category = new Categories(putProductRequestDTO.getCategoryId());
//        product.setCategories(category);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Products product = this.findProductByProductId(productId);

        productRepository.delete(product);
    }
}
