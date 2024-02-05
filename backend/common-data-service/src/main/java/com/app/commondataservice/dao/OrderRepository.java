/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.dao;

import com.app.commondataservice.entity.Orders;
import com.app.commondataservice.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUsers(Users user);
}
