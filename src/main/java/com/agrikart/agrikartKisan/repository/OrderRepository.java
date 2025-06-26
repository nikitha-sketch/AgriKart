package com.agrikart.agrikartKisan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agrikart.agrikartKisan.model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
	// OrderRepository.java

	List<Order> findByUser_Id(Long userId); // ✅ NOT findByUserId


}
