package edu.poly.shop.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.repository.OrderRepository;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
	
		this.orderRepository = orderRepository;
	}

	@Override
	public <S extends Order> S save(S entity) {
		return orderRepository.save(entity);
	}

	@Override
	public <S extends Order> Optional<S> findOne(Example<S> example) {
		return orderRepository.findOne(example);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return orderRepository.findAll(sort);
	}

	@Override
	public List<Order> findAllById(Iterable<Long> ids) {
		return orderRepository.findAllById(ids);
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public <S extends Order> List<S> saveAll(Iterable<S> entities) {
		return orderRepository.saveAll(entities);
	}

	@Override
	public <S extends Order> S saveAndFlush(S entity) {
		return orderRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return orderRepository.existsById(id);
	}

	@Override
	public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
		return orderRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
		return orderRepository.findAll(example, pageable);
	}

	@Override
	public void deleteAllInBatch(Iterable<Order> entities) {
		orderRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return orderRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		orderRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		orderRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		orderRepository.deleteAllInBatch();
	}

	@Override
	public void deleteAll(Iterable<? extends Order> entities) {
		orderRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		orderRepository.deleteAll();
	}

	@Override
	public Order getById(Long id) {
		return orderRepository.getById(id);
	}

	
	


	@Override
	public List<Order> findByOrderIdContaining(Long oderId) {
		return orderRepository.findByOrderIdContaining(oderId);
	}

	



	@Override
	public Page<Order> findByOrderIdContaining(Long oderId, Pageable pageable) {
		return orderRepository.findByOrderIdContaining(oderId, pageable);
	}
	
	@Override
	public void create(Order order, List<OrderDetail> details) {
		orderService.save(order);
		
		for (OrderDetail orderDetail : details) {
			orderDetailService.save(orderDetail);
		}
	}

	@Override
	public List<Order> findByCustomerId(Long customerId) {
		return orderRepository.findByCustomerId(customerId);
	}

	@Override
	public Long countCustomerId() {
		return orderRepository.countCustomerId();
	}

	@Override
	public Double sumAmountOrders() {
		return orderRepository.sumAmountOrders();
	}

	
	

	
	
	

	

	
	
	
	
	
}
