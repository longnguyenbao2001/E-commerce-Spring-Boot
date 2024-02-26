/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.service;

import com.app.orderservice.dto.CreateOrderDetailRequestDTO;
import com.app.orderservice.dto.DeleteOrderDetailRequestDTO;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.dto.PutOrderDetailRequestDTO;
import com.app.orderservice.entity.OrderDetails;
import com.app.orderservice.entity.OrderDetailsPK;
import com.app.orderservice.exception.DataNotFoundException;
import java.util.List;

/**
 *
 * @author user
 */
public interface OrderService {

    public List<OrderDTO> getMyOrders(Long userId);

    public void createOrder(Long refUserId);

    public void deleteOrder(Long orderId);

    public List<OrderDetailDTO> getOrderDetails(Long orderId);

    public void createOrderDetails(Long orderId,
            List<CreateOrderDetailRequestDTO> listCreateOrderDetailRequestDTO);

    public OrderDetails findOrderDetailByOrderDetailsPK(OrderDetailsPK orderDetailsPK)
            throws DataNotFoundException;

    public void putOrderDetails(Long orderId,
            List<PutOrderDetailRequestDTO> listPutOrderDetailRequestDTO)
            throws DataNotFoundException;

    public void deleteOrderDetails(Long orderId,
            List<DeleteOrderDetailRequestDTO> listDeleteOrderDetailRequestDTO)
            throws DataNotFoundException;

}
