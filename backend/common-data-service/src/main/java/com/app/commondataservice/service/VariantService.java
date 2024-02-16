/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service;

import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.CreateVariantRequestDTO;
import com.app.commondataservice.dto.CreateVariantValueRequestDTO;
import com.app.commondataservice.dto.PutVariantRequestDTO;
import com.app.commondataservice.dto.PutVariantValueRequestDTO;
import com.app.commondataservice.dto.VariantDTO;
import com.app.commondataservice.entity.VariantValues;
import com.app.commondataservice.entity.Variants;
import com.app.commondataservice.exception.DataNotFoundException;
import java.util.List;

/**
 *
 * @author user
 */
public interface VariantService {

    public VariantValues findVariantValueByVariantValueId(Long variantValueId)
            throws DataNotFoundException;

    public Variants findVariantByVariantId(Long variantId)
            throws DataNotFoundException;

    public List<VariantDTO> getVariantsByProductId(Long productId);

    public VariantValues createVariantValue(
            CreateVariantValueRequestDTO createVariantValueRequestDTO);

    public Variants createVariantData(
            Long productId,
            CreateVariantRequestDTO createVariantRequestDTO,
            Long refUserId);

    public void createVariant(
            Long productId,
            CreateVariantRequestDTO createVariantRequestDTO,
            Long refUserId);

    public VariantValues putVariantValue(
            PutVariantValueRequestDTO putVariantValueRequestDTO)
            throws DataNotFoundException;

    public Variants putVariantData(
            Long productId,
            PutVariantRequestDTO putVariantRequestDTO,
            Long refUserId)
            throws DataNotFoundException;

    public void putVariant(Long productId,
            PutVariantRequestDTO putVariantRequestDTO,
            AuthUserDTO authUserDTO,
            Long refUserId)
            throws DataNotFoundException;

    public void deleteVariant(Long variantId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException;
}
