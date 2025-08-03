package com.agrikart.agrikartKisan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrikart.agrikartKisan.dto.OrderDTO;
import com.agrikart.agrikartKisan.dto.OrderItemDTO;
import com.agrikart.agrikartKisan.model.Order;
import com.agrikart.agrikartKisan.model.OrderItem;
import com.agrikart.agrikartKisan.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    public Order placeOrder(Order order) {
//        // Link order to each order item & ensure required fields are set
//        if (order.getItems() != null) {
//            for (OrderItem item : order.getItems()) {
//                item.setOrder(order); // Important to set back-reference
//                if (item.getQuantity() == 0) {
//                    throw new IllegalArgumentException("Quantity must be greater than 0");
//                }
//            }
//        }
//        return orderRepository.save(order);
//    }

    public Order placeOrder(Order order) {
        int totalQuantity = 0;

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order); // Set back-reference
                if (item.getQuantity() == null || item.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than 0");
                }
                totalQuantity += item.getQuantity(); // accumulate quantity
            }
        }

        order.setQuantity(totalQuantity);        // ✅ Set total quantity
        order.setStatus("Confirmed");            // ✅ Set default status
        return orderRepository.save(order);
    }


 // OrderService.java

    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }



    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item ->
                new OrderItemDTO(
                    item.getProductName(),
                    item.getProductCode(),
                    item.getProductPrice(),
                    item.getQuantity()
                )
            ).toList();

            return new OrderDTO(
                order.getId(),
                order.getUser().getFullName(),
                order.getUser().getId(),
                itemDTOs,
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getProductName() // ✅ ensure this line exists
            );
        }).toList();
    }


    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item ->
                new OrderItemDTO(
                    item.getProductName(),
                    item.getProductCode(),
                    item.getProductPrice(),
                    item.getQuantity()
                )
            ).toList();

            OrderDTO dto = new OrderDTO(
                order.getId(),
                order.getUser().getFullName(),
                order.getUser().getId(),
                itemDTOs,
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getProductName() 
            );

            // ✅ Set productName (used in admin dashboard)
            dto.setProductName(order.getProductName());

            return dto;
        }).toList();
    }

    public boolean cancelOrder(Long orderId, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (order.getUser().getId().equals(userId)) {
                orderRepository.deleteById(orderId); // or update status if soft delete
                return true;
            }
        }
        return false;
    }


    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
        orderRepository.deleteById(id);
    }

}
