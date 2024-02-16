/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.service;

import com.app.orderservice.dto.AuthUserDTO;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.exception.DataNotFoundException;
import java.util.List;

/**
 *
 * @author user
 */
public interface OrderService {

    public List<OrderDTO> getMyOrdersList(Long userId);

    public List<OrderDetailDTO> getOrderDetails(Long orderId);
}
