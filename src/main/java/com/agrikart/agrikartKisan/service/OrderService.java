package com.agrikart.agrikartKisan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrikart.agrikartKisan.model.Order;
import com.agrikart.agrikartKisan.model.Product;
import com.agrikart.agrikartKisan.repository.OrderRepository;
import com.agrikart.agrikartKisan.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(Order order) {
        double total = order.getProducts()
                            .stream()
                            .mapToDouble(Product::getPrice)
                            .sum();
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
