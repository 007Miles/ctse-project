package com.ctse.orderservice.service;

import com.ctse.orderservice.dto.OrderLineItemDto;
import com.ctse.orderservice.dto.OrderRequest;
import com.ctse.orderservice.model.Order;
import com.ctse.orderservice.model.OrderLineItem;
import com.ctse.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItemList);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrderById(Long id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
        List<OrderLineItem> newOrderLineItemList = orderRequest.getOrderLineItemList()
                .stream()
                .map(this::mapToDto)
                .toList();
        orderLineItemList.addAll(newOrderLineItemList);
        order.setOrderLineItemList(orderLineItemList);
        return orderRepository.save(order);
    }

    public String deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return "No order exist with the given id: " + id;
        orderRepository.deleteById(id);
        return "Successfully deleted order with id: " + id;
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        return mapper.map(orderLineItemDto, OrderLineItem.class);
    }
}
