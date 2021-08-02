package edu.poly.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.shop.domain.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByOrderIdContaining(Long oderId);
	Page<Order> findByOrderIdContaining(Long oderId, Pageable pageable);
	
	 @Query("select o from Order o where o.customer.customerId = ?1")
	  List<Order> findByCustomerId(Long customerId);
	 
	 @Query("select count(o.orderId) from Order o")
	 Long countCustomerId();
	 @Query("select sum(o.amount) from Order o")
	 Double sumAmountOrders();
	
}
