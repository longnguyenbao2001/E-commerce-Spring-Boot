/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.orderservice.dao;

import com.app.orderservice.entity.OrderDetails;
import com.app.orderservice.entity.OrderDetailsPK;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findByOrders_Id(Long orderId);

    Optional<OrderDetails> findByOrderDetailsPK(OrderDetailsPK orderDetailsPK);
}
