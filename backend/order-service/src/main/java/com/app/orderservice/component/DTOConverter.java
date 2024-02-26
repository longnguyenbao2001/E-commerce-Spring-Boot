/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.component;

import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.entity.OrderDetails;
import com.app.orderservice.entity.Orders;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO convertOrderToDTO(Orders order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        return orderDTO;
    }

    public OrderDetailDTO convertOrderDetailToDTO(OrderDetails orderDetail) {
//        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetails, OrderDetailDTO.class);
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setOrderId(orderDetail.getOrderDetailsPK().getOrderId());
        orderDetailDTO.setProductId(orderDetail.getOrderDetailsPK().getProductId());
        orderDetailDTO.setVariantId(orderDetail.getOrderDetailsPK().getVariantId());

        return orderDetailDTO;
    }
}
