package com.agrikart.agrikartKisan.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.agrikart.agrikartKisan.dto.OrderDTO;
import com.agrikart.agrikartKisan.dto.OrderItemDTO;
import com.agrikart.agrikartKisan.model.Order;
import com.agrikart.agrikartKisan.model.OrderItem;
import com.agrikart.agrikartKisan.model.Product;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.repository.ProductRepository;
import com.agrikart.agrikartKisan.repository.UserRepository;
import com.agrikart.agrikartKisan.service.OrderService;
import com.agrikart.agrikartKisan.service.UserService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Place Order (User)
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderDTO orderDto) {
        Optional<User> userOpt = userRepository.findById(orderDto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found with ID: " + orderDto.getUserId());
        }

        User user = userOpt.get();
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(orderDto.getStatus());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;
        StringBuilder productNames = new StringBuilder();

        for (OrderItemDTO itemDto : orderDto.getOrderItems()) {
            Optional<Product> productOpt = productRepository.findById(itemDto.getProductId());
            if (productOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid product ID: " + itemDto.getProductId());
            }

            Product product = productOpt.get();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImageUrl());
            orderItem.setProductPrice(product.getPrice());
            orderItem.setProductCode(product.getCode());

            orderItems.add(orderItem);
            total += product.getPrice() * itemDto.getQuantity();

            if (productNames.length() > 0) productNames.append(", ");
            productNames.append(product.getName());
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setProductName(productNames.toString());

        Order savedOrder = orderService.placeOrder(order);

        // ✅ Build response DTO
        List<OrderItemDTO> itemDTOs = savedOrder.getItems().stream().map(item ->
            new OrderItemDTO(
                item.getProductName(),
                item.getProductCode(),
                item.getProductPrice(),
                item.getQuantity()
            )
        ).toList();

        OrderDTO responseDto = new OrderDTO(
            savedOrder.getId(),
            savedOrder.getUser().getFullName(),      // ✅ Only if your constructor accepts this
            savedOrder.getUser().getId(),
            itemDTOs,
            savedOrder.getOrderDate(),
            savedOrder.getTotalAmount(),
            savedOrder.getStatus(),
            savedOrder.getProductName()
        );

        return ResponseEntity.ok(responseDto);
    }


    // ✅ Admin: Get all orders
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            List<OrderDTO> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(403).body("Only admin can view all orders.");
        }
    }

    // ✅ Public: Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        return orderOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ User: Get orders by User ID

    @GetMapping("/by-user")
    public ResponseEntity<?> getOrdersByUser(@RequestParam Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }


    // ✅ Admin: Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id,
                                         @RequestParam String email,
                                         @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            Optional<Order> orderOpt = orderService.getOrderById(id);
            if (orderOpt.isPresent()) {
                orderService.deleteOrder(id);
                return ResponseEntity.ok("Order deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Order not found.");
            }
        } else {
            return ResponseEntity.status(403).body("Only admin can delete orders.");
        }
    }

    @DeleteMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrderByUser(@PathVariable Long orderId,
                                               @RequestParam String email,
                                               @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null) {
            boolean success = orderService.cancelOrder(orderId, user.getId());
            if (success) {
                return ResponseEntity.ok("Order cancelled successfully.");
            } else {
                return ResponseEntity.status(403).body("You can only cancel your own orders.");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }

    // ✅ Admin: Get default list of orders
    @GetMapping
    public ResponseEntity<?> getOrdersDefault(@RequestParam String email,
                                              @RequestParam String password) {
        User user = userService.loginUser(email, password);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.ok(orderService.getAllOrders());
        } else {
            return ResponseEntity.status(403).body("Only admin can view all orders.");
        }
    }
}
