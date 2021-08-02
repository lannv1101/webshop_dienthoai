package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.OrderDetail;

public interface OrderDetailService {

	OrderDetail getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends OrderDetail> entities);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(OrderDetail entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	long count();

	<S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends OrderDetail> S saveAndFlush(S entity);

	<S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

	Optional<OrderDetail> findById(Long id);

	List<OrderDetail> findAllById(Iterable<Long> ids);

	List<OrderDetail> findAll(Sort sort);

	List<OrderDetail> findAll();

	Page<OrderDetail> findAll(Pageable pageable);

	<S extends OrderDetail> S save(S entity);

	Integer countByQuantity();

	


}
