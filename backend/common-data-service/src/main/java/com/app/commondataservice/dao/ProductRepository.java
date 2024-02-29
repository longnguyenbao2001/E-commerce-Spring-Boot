/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.commondataservice.dao;

import com.app.commondataservice.entity.Products;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface ProductRepository extends JpaRepository<Products, Long> {

    Integer countByNameContaining(String keyword);

    Integer countByNameContainingAndCategoriesList_IdIn(String keyword, List<Long> categoryIds);

    List<Products> findByNameContaining(String keyword, Pageable pageable);

    List<Products> findByNameContainingAndCategoriesList_IdIn(String keyword, List<Long> categoryIds, Pageable pageable);
}
