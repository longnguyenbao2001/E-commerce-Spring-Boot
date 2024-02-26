/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service.impl;

import com.app.userservice.dao.ShipAddressRepository;
import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.dto.CreateShipAddressRequestDTO;
import com.app.userservice.dto.PutShipAddressRequestDTO;
import com.app.userservice.dto.ShipAddressDTO;
import com.app.userservice.service.ShipAddressService;
import com.app.userservice.service.UserService;
import com.app.userservice.service.UserPermissionService;
import com.app.userservice.entity.Users;
import com.app.userservice.entity.ShipAddresses;
import com.app.userservice.exception.DataNotFoundException;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotFoundException;
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
public class ShipAddressServiceImpl implements ShipAddressService {

    @Autowired
    private ShipAddressRepository shipAddressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserPermissionService userPermissionService;

    private Optional<ShipAddresses> findByShipAddressId(Long addressId) throws DataNotFoundException {
        Optional<ShipAddresses> res = shipAddressRepository.findById(addressId);
        if (!res.isPresent()) {
            throw new DataNotFoundException();
        }

        return res;
    }

    @Override
    public List<ShipAddressDTO> getShipAddressByUser(AuthUserDTO authUserDTO) throws UserNotFoundException {
        Optional<Users> existingUser = userService.findUserByUserId(authUserDTO.getId());

        List<ShipAddressDTO> res = new ArrayList<>();
        for (ShipAddresses shipAddresses : shipAddressRepository.findByUsers_Id(existingUser.get().getId())) {
            ShipAddressDTO shipAddressDTO = new ShipAddressDTO();
            shipAddressDTO.setAddressLine1(shipAddresses.getAddressLine1());
            shipAddressDTO.setAddressLine2(shipAddresses.getAddressLine2());
            shipAddressDTO.setCity(shipAddresses.getCity());
            shipAddressDTO.setCountry(shipAddresses.getCountry());
            shipAddressDTO.setPhoneNumber(shipAddresses.getPhoneNumber());

            res.add(shipAddressDTO);
        }

        return res;
    }

    @Override
    public void createShipAddress(CreateShipAddressRequestDTO createShipAddressRequestDTO, Long refUserId)
            throws UserNotFoundException {
        Optional<Users> existingUser = userService.findUserByUserId(refUserId);

        ShipAddresses shipAddresses = new ShipAddresses();
        shipAddresses.setAddressLine1(createShipAddressRequestDTO.getAddressLine1());
        shipAddresses.setAddressLine2(createShipAddressRequestDTO.getAddressLine2());
        shipAddresses.setCity(createShipAddressRequestDTO.getCity());
        shipAddresses.setCountry(createShipAddressRequestDTO.getCountry());
        shipAddresses.setPhoneNumber(createShipAddressRequestDTO.getPhoneNumber());

        shipAddresses.setUsers(existingUser.get());

        shipAddressRepository.save(shipAddresses);
    }

    @Override
    @Transactional
    public void putShipAddress(PutShipAddressRequestDTO putShipAddressRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws UserNotFoundException, DataNotFoundException, UserHasNoPermissionException {
        Optional<Users> opRefUser = userService.findUserByUserId(refUserId);
        Users refUser = opRefUser.get();
        Optional<ShipAddresses> opShipAddresses = this.findByShipAddressId(putShipAddressRequestDTO.getId());
        ShipAddresses shipAddresses = opShipAddresses.get();

        //check ref userid is the owner of address
        if (!(userPermissionService.isOwner(refUser.getId(), shipAddresses.getUsers().getId())
                || userPermissionService.isAdmin(authUserDTO))) {
            throw new UserHasNoPermissionException();
        }

        shipAddresses.setAddressLine1(putShipAddressRequestDTO.getAddressLine1());
        shipAddresses.setAddressLine2(putShipAddressRequestDTO.getAddressLine2());
        shipAddresses.setCity(putShipAddressRequestDTO.getCity());
        shipAddresses.setCountry(putShipAddressRequestDTO.getCountry());
        shipAddresses.setPhoneNumber(putShipAddressRequestDTO.getPhoneNumber());

        shipAddresses.setUsers(refUser);

        shipAddressRepository.save(shipAddresses);
    }

    @Override
    @Transactional
    public void deleteShipAddress(Long addressId, AuthUserDTO authUserDTO, Long refUserId)
            throws UserNotFoundException, DataNotFoundException, UserHasNoPermissionException {
        Optional<Users> opRefUser = userService.findUserByUserId(refUserId);
        Users refUser = opRefUser.get();
        Optional<ShipAddresses> opShipAddresses = this.findByShipAddressId(addressId);
        ShipAddresses shipAddresses = opShipAddresses.get();

        if (!(userPermissionService.isOwner(refUser.getId(), shipAddresses.getUsers().getId())
                || userPermissionService.isAdmin(authUserDTO))) {
            throw new UserHasNoPermissionException();
        }

        shipAddressRepository.delete(shipAddresses);
    }
}
