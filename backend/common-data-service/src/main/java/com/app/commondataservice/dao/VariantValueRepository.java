/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dao;

import com.app.commondataservice.entity.VariantValues;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface VariantValueRepository extends JpaRepository<VariantValues, Long> {

}
