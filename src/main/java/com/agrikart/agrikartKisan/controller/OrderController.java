package com.agrikart.agrikartKisan.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrikart.agrikartKisan.dto.OrderDTO;
import com.agrikart.agrikartKisan.model.Order;
import com.agrikart.agrikartKisan.model.Product;
import com.agrikart.agrikartKisan.model.User;
import com.agrikart.agrikartKisan.repository.ProductRepository;
import com.agrikart.agrikartKisan.repository.UserRepository;
import com.agrikart.agrikartKisan.service.OrderService;
import com.agrikart.agrikartKisan.service.UserService;

import jakarta.validation.Valid;

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

    // ✅ Users place orders
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderDTO orderDto) {
        Optional<User> userOpt = userRepository.findById(orderDto.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found with ID: " + orderDto.getUserId());
        }

        List<Product> products = productRepository.findAllById(orderDto.getProductIds());
        if (products.isEmpty() || products.size() != orderDto.getProductIds().size()) {
            return ResponseEntity.badRequest().body("One or more products are invalid");
        }

        double total = products.stream()
                .mapToDouble(Product::getPrice)
                .sum() * orderDto.getQuantity();

        

        Order order = new Order();
        order.setUser(userOpt.get());
        order.setProducts(products);
        order.setQuantity(orderDto.getQuantity());
        order.setStatus(orderDto.getStatus());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(total);

        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    // ✅ Admin Only: View all orders
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password); // ✅ use User directly
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.ok(orderService.getAllOrders());
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

    // ✅ User: Get orders by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    // ✅ Admin Only: Delete any order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id,
                                         @RequestParam String email,
                                         @RequestParam String password) {
        User user = userService.loginUser(email, password); // ✅ use User directly
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            orderService.deleteOrder(id);
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.status(403).body("Only admin can delete orders.");
        }
    }


    // ✅ Admin Only: View default orders (if needed)
    @GetMapping
    public ResponseEntity<?> getOrdersDefault(@RequestParam String email, @RequestParam String password) {
        User user = userService.loginUser(email, password);  // ✅ Use User directly
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.ok(orderService.getAllOrders());
        } else {
            return ResponseEntity.status(403).body("Only admin can view all orders.");
        }
    }

}
