/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.service;

import com.app.userservice.dto.CreateShipAddressRequestDTO;
import com.app.userservice.dto.PutShipAddressRequestDTO;
import com.app.userservice.dto.ShipAddressDTO;
import com.app.userservice.entity.Users;
import com.app.userservice.exception.DataNotFoundException;
import com.app.userservice.exception.UserHasNoPermissionException;
import com.app.userservice.exception.UserNotExistedException;
import java.util.List;

/**
 *
 * @author user
 */
public interface ShipAddressService {

    public void createShipAddress(CreateShipAddressRequestDTO createShipAddressRequestDTO)
            throws UserNotExistedException, UserHasNoPermissionException;

    public List<ShipAddressDTO> getShipAddress(Long userId)
            throws UserNotExistedException;

    public void putShipAddress(PutShipAddressRequestDTO putShipAddressRequestDTO)
            throws UserNotExistedException, DataNotFoundException, UserHasNoPermissionException;

    public void deleteShipAddress(Long addressId, Users currUser, Long refUserId)
            throws UserNotExistedException, DataNotFoundException, UserHasNoPermissionException;
}
