/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.service.impl;

import com.app.orderservice.component.DTOConverter;
import com.app.orderservice.dao.OrderDetailRepository;
import com.app.orderservice.dao.OrderRepository;
import com.app.orderservice.dto.CreateOrderDetailRequestDTO;
import com.app.orderservice.dto.DeleteOrderDetailRequestDTO;
import com.app.orderservice.dto.OrderDTO;
import com.app.orderservice.dto.OrderDetailDTO;
import com.app.orderservice.dto.PutOrderDetailRequestDTO;
import com.app.orderservice.entity.OrderDetails;
import com.app.orderservice.entity.OrderDetailsPK;
import com.app.orderservice.entity.Orders;
import com.app.orderservice.entity.Users;
import com.app.orderservice.exception.DataNotFoundException;
import com.app.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
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
    public List<OrderDTO> getMyOrders(Long userId) {
        List<OrderDTO> myOrdersList = new ArrayList<>();

        for (Orders order : orderRepository.findByUsers_Id(userId)) {
            myOrdersList.add(dtoConverter.convertOrderToDTO(order));
        }

        return myOrdersList;
    }

    @Override
    public void createOrder(Long refUserId) {
        Users user = new Users(refUserId);

        Orders order = new Orders();
        order.setUsers(user);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Orders order = new Orders(orderId);

        orderRepository.delete(order);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetails(Long orderId) {
        List<OrderDetails> opOrderDetailsList = orderDetailRepository.findByOrders_Id(orderId);

        List<OrderDetailDTO> orderDetailsDTOList = new ArrayList<>();
        for (OrderDetails orderDetail : opOrderDetailsList) {
            orderDetailsDTOList.add(dtoConverter.convertOrderDetailToDTO(orderDetail));
        }

        return orderDetailsDTOList;
    }

    @Override
    @Transactional
    public void createOrderDetails(Long orderId,
            List<CreateOrderDetailRequestDTO> listCreateOrderDetailRequestDTO) {
        for (CreateOrderDetailRequestDTO createOrderDetailRequestDTO : listCreateOrderDetailRequestDTO) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setQuantity(createOrderDetailRequestDTO.getQuantity());

            OrderDetailsPK orderDetailsPK = new OrderDetailsPK();
            orderDetailsPK.setOrderId(orderId);
            orderDetailsPK.setProductId(createOrderDetailRequestDTO.getProductId());
            orderDetailsPK.setVariantId(createOrderDetailRequestDTO.getVariantId());

            orderDetail.setOrderDetailsPK(orderDetailsPK);

            orderDetailRepository.save(orderDetail);
        }
    }

    @Override
    public OrderDetails findOrderDetailByOrderDetailsPK(OrderDetailsPK orderDetailsPK)
            throws DataNotFoundException {
        Optional<OrderDetails> opOrderDetail = orderDetailRepository.findByOrderDetailsPK(orderDetailsPK);
        if (!opOrderDetail.isPresent()) {
            throw new DataNotFoundException();
        }

        return opOrderDetail.get();
    }

    @Override
    @Transactional
    public void putOrderDetails(Long orderId,
            List<PutOrderDetailRequestDTO> listPutOrderDetailRequestDTO)
            throws DataNotFoundException {
        for (PutOrderDetailRequestDTO putOrderDetailRequestDTO : listPutOrderDetailRequestDTO) {
            OrderDetailsPK orderDetailsPK = new OrderDetailsPK();
            orderDetailsPK.setOrderId(orderId);
            orderDetailsPK.setProductId(putOrderDetailRequestDTO.getProductId());
            orderDetailsPK.setVariantId(putOrderDetailRequestDTO.getVariantId());
            OrderDetails orderDetail = this.findOrderDetailByOrderDetailsPK(orderDetailsPK);

            orderDetail.setQuantity(putOrderDetailRequestDTO.getQuantity());

            orderDetailRepository.save(orderDetail);
        }
    }

    @Override
    @Transactional
    public void deleteOrderDetails(Long orderId,
            List<DeleteOrderDetailRequestDTO> listDeleteOrderDetailRequestDTO)
            throws DataNotFoundException {
        for (DeleteOrderDetailRequestDTO deleteOrderDetailRequestDTO : listDeleteOrderDetailRequestDTO) {
            OrderDetailsPK orderDetailsPK = new OrderDetailsPK();
            orderDetailsPK.setOrderId(orderId);
            orderDetailsPK.setProductId(deleteOrderDetailRequestDTO.getProductId());
            orderDetailsPK.setVariantId(deleteOrderDetailRequestDTO.getVariantId());
            OrderDetails orderDetail = this.findOrderDetailByOrderDetailsPK(orderDetailsPK);

            orderDetailRepository.delete(orderDetail);
        }
    }
}
