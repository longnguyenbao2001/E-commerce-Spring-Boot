/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.AuthUserDTO;
import com.app.userservice.dto.CreateShipAddressRequestDTO;
import com.app.userservice.dto.PutShipAddressRequestDTO;
import com.app.userservice.dto.ShipAddressDTO;
import com.app.userservice.exception.DataNotFoundException;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotFoundException;
import java.util.List;

/**
 *
 * @author user
 */
public interface ShipAddressService {

    public List<ShipAddressDTO> getShipAddressByUser(AuthUserDTO authUserDTO)
            throws UserNotFoundException;

    public void createShipAddress(CreateShipAddressRequestDTO createShipAddressRequestDTO, Long refUserId)
            throws UserNotFoundException, UserHasNoPermissionException;

    public void putShipAddress(PutShipAddressRequestDTO putShipAddressRequestDTO, AuthUserDTO authUserDTO, Long refUserId)
            throws UserNotFoundException, DataNotFoundException, UserHasNoPermissionException;

    public void deleteShipAddress(Long addressId, AuthUserDTO authUserDTO, Long refUserId)
            throws UserNotFoundException, DataNotFoundException, UserHasNoPermissionException;
}
