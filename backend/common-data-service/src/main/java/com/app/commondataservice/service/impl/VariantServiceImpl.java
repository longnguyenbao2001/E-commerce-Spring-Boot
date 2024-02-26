/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.component.DTOConverter;
import com.app.commondataservice.dao.VariantRepository;
import com.app.commondataservice.dao.VariantValueRepository;
import com.app.commondataservice.dto.AuthUserDTO;
import com.app.commondataservice.dto.CreateVariantRequestDTO;
import com.app.commondataservice.dto.CreateVariantValueRequestDTO;
import com.app.commondataservice.dto.GetVariantsQuantityResponseDTO;
import com.app.commondataservice.dto.PutVariantRequestDTO;
import com.app.commondataservice.dto.PutVariantValueRequestDTO;
import com.app.commondataservice.dto.VariantDTO;
import com.app.commondataservice.entity.Products;
import com.app.commondataservice.entity.VariantLabels;
import com.app.commondataservice.entity.VariantValues;
import com.app.commondataservice.entity.Variants;
import com.app.commondataservice.exception.DataNotFoundException;
import com.app.commondataservice.service.VariantService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class VariantServiceImpl implements VariantService {

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private VariantValueRepository variantValueRepository;

    @Autowired
    private DTOConverter dtoConverter;

    @Override
    public VariantValues findVariantValueByVariantValueId(Long variantValueId)
            throws DataNotFoundException {
        Optional<VariantValues> opVariantValue = variantValueRepository.findById(variantValueId);

        if (!opVariantValue.isPresent()) {
            throw new DataNotFoundException();
        }

        return opVariantValue.get();
    }

    @Override
    public Variants findVariantByVariantId(Long variantId)
            throws DataNotFoundException {
        Optional<Variants> opVariant = variantRepository.findById(variantId);

        if (!opVariant.isPresent()) {
            throw new DataNotFoundException();
        }

        return opVariant.get();
    }

    @Override
    @Transactional
    public VariantValues createVariantValue(
            CreateVariantValueRequestDTO createVariantValueRequestDTO) {
        VariantValues variantValues = new VariantValues();

        variantValues.setName(createVariantValueRequestDTO.getName());

        VariantLabels variantLabels = new VariantLabels(createVariantValueRequestDTO.getVariantLabelId());
        variantValues.setVariantLabels(variantLabels);

        return variantValueRepository.save(variantValues);
    }

    @Override
    @Transactional
    public Variants createVariantData(
            Long productId,
            CreateVariantRequestDTO createVariantRequestDTO,
            Long refUserId) {
        Products product = new Products(productId);

        Variants variant = new Variants();
        variant.setProducts(product);
        variant.setUnitPrice(createVariantRequestDTO.getUnitPrice());
        variant.setQuantity(createVariantRequestDTO.getQuantity());

        List<VariantValues> listVariantValues = new ArrayList<>();

        for (CreateVariantValueRequestDTO createVariantValueRequestDTO : createVariantRequestDTO.getListVariantValues()) {
            VariantValues variantValues = this.createVariantValue(createVariantValueRequestDTO);
            listVariantValues.add(variantValues);
        }

        variant.setVariantValuesList(listVariantValues);

        return variant;
    }

    @Override
    @Transactional
    public void createVariant(
            Long productId,
            CreateVariantRequestDTO createVariantRequestDTO,
            Long refUserId) {
        Variants variant = this.createVariantData(productId, createVariantRequestDTO, refUserId);
        List<VariantValues> listVariantValues = variant.getVariantValuesList();

        variant = variantRepository.save(variant);
        for (VariantValues variantValues : listVariantValues) {
            variantValues.setVariantsList(new ArrayList<>());
            variantValues.getVariantsList().add(variant);

            variantValueRepository.save(variantValues);
        }
    }

    @Override
    public List<VariantDTO> getVariantsByProductId(Long productId) {
        List<VariantDTO> res = new ArrayList<>();

        for (Variants variant : variantRepository.findByProducts_Id(productId)) {
            res.add(dtoConverter.convertVariantToDTO(variant));
        }

        return res;
    }

    @Override
    @Transactional
    public VariantValues putVariantValue(
            PutVariantValueRequestDTO putVariantValueRequestDTO)
            throws DataNotFoundException {
        VariantValues variantValues = this.findVariantValueByVariantValueId(putVariantValueRequestDTO.getId());

        variantValues.setName(putVariantValueRequestDTO.getName());

        VariantLabels variantLabels = new VariantLabels(putVariantValueRequestDTO.getVariantLabelId());
        variantValues.setVariantLabels(variantLabels);

        return variantValueRepository.save(variantValues);
    }

    @Override
    @Transactional
    public Variants putVariantData(
            Long productId,
            PutVariantRequestDTO putVariantRequestDTO,
            Long refUserId)
            throws DataNotFoundException {
        Products product = new Products(productId);

        Variants variant = this.findVariantByVariantId(putVariantRequestDTO.getId());
        variant.setProducts(product);
        variant.setUnitPrice(putVariantRequestDTO.getUnitPrice());
        variant.setQuantity(putVariantRequestDTO.getQuantity());

        List<VariantValues> listVariantValues = new ArrayList<>();

        for (PutVariantValueRequestDTO putVariantValueRequestDTO : putVariantRequestDTO.getListVariantValues()) {
            VariantValues variantValues = this.putVariantValue(putVariantValueRequestDTO);
            listVariantValues.add(variantValues);
        }

        variant.setVariantValuesList(listVariantValues);

        return variant;
    }

    @Override
    @Transactional
    public void putVariant(
            Long productId,
            PutVariantRequestDTO putVariantRequestDTO,
            AuthUserDTO authUserDTO,
            Long refUserId)
            throws DataNotFoundException {
        Variants variant = this.putVariantData(productId, putVariantRequestDTO, refUserId);
        List<VariantValues> listVariantValues = variant.getVariantValuesList();

        variant = variantRepository.save(variant);
        for (VariantValues variantValue : listVariantValues) {
            variantValue.setVariantsList(new ArrayList<>());
            variantValue.getVariantsList().add(variant);

            variantValueRepository.save(variantValue);
        }
    }

    @Override
    public void deleteVariant(Long variantId, AuthUserDTO authUserDTO, Long refUserId)
            throws DataNotFoundException {
        Variants variant = this.findVariantByVariantId(variantId);

        List<VariantValues> listVariantValues = variant.getVariantValuesList();
        for (VariantValues variantValue : listVariantValues) {

            variantValueRepository.delete(variantValue);
        }

        variantRepository.delete(variant);
    }

    @Override
    public List<GetVariantsQuantityResponseDTO> getVariantsQuantity(List<Long> listVariantsIds)
            throws DataNotFoundException {
        List<GetVariantsQuantityResponseDTO> res = new ArrayList<>();

        Variants variant;
        for (Long variantId : listVariantsIds) {
            variant = this.findVariantByVariantId(variantId);
            res.add(dtoConverter.convertVariantToQuantityDTO(variant));
        }

        return res;
    }
}
