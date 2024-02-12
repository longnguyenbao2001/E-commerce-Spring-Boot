/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.userservice.dao;

import com.app.userservice.entity.ShipAddresses;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface ShipAddressRepository extends JpaRepository<ShipAddresses, Long> {

    List<ShipAddresses> findByUsers_Id(Long userId);
}
