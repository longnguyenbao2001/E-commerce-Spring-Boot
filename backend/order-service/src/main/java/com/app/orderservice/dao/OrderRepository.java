/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.dao;

import com.app.orderservice.entity.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUsers_Id(Long userId);
}
