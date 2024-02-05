/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.commondataservice.service;

import java.util.List;
import com.app.commondataservice.entity.Orders;
import com.app.commondataservice.entity.Users;

/**
 *
 * @author user
 */
public interface OrderService {

    List<Orders> getMyOrders(Users user);
}
