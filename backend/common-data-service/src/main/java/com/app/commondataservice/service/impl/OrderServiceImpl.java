/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.commondataservice.service.impl;

import com.app.commondataservice.dao.OrderRepository;
import com.app.commondataservice.entity.Orders;
import com.app.commondataservice.entity.Users;
import com.app.commondataservice.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Orders> getMyOrders(Users user) {
        return orderRepository.findByUsers(user);
    }

}
