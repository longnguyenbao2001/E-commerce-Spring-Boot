/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.service.impl;

import com.app.orderservice.component.DTOConverter;
import com.app.orderservice.dao.OrderDetailRepository;
import com.app.orderservice.dao.OrderRepository;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.entity.OrderDetails;
import com.app.orderservice.entity.Orders;
import com.app.orderservice.exception.DataNotFoundException;
import com.app.orderservice.service.OrderService;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private DTOConverter dtoConverter;

    @Override
    public List<OrderDTO> getMyOrdersList(Long userId) {
        List<OrderDTO> myOrdersList = new ArrayList<>();

        for (Orders order : orderRepository.findByUsers_Id(userId)) {
            myOrdersList.add(dtoConverter.convertOrderToDTO(order));
        }

        return myOrdersList;
    }

    @Override
    public List<OrderDetailDTO> getOrderDetails(Long orderId) {
        List<OrderDetails> opOrderDetailsList = orderDetailRepository.findByOrders_Id(orderId);

        List<OrderDetailDTO> orderDetailsDTOList = new ArrayList<>();
        for (OrderDetails orderDetail : opOrderDetailsList) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

            orderDetailDTO.setOrderId(orderId);
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            orderDetailDTO.setProductId(orderDetail.getProductId());
            orderDetailDTO.setVariantId(orderDetail.getVariantId());

            orderDetailsDTOList.add(orderDetailDTO);
        }

        return orderDetailsDTOList;
    }
}
