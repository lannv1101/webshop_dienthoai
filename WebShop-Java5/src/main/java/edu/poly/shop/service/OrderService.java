package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;

public interface OrderService {

	Order getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Order> entities);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Order entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	long count();

	void deleteAllInBatch(Iterable<Order> entities);

	<S extends Order> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Order> S saveAndFlush(S entity);

	<S extends Order> List<S> saveAll(Iterable<S> entities);

	Optional<Order> findById(Long id);

	List<Order> findAllById(Iterable<Long> ids);

	List<Order> findAll(Sort sort);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	<S extends Order> Optional<S> findOne(Example<S> example);

	<S extends Order> S save(S entity);

	Page<Order> findByOrderIdContaining(Long oderId, Pageable pageable);

	List<Order> findByOrderIdContaining(Long oderId);

	void create(Order order, List<OrderDetail> details);

	List<Order> findByCustomerId(Long customerId);

	Long countCustomerId();

	Double sumAmountOrders();

	

	



	



	

	
	

}
