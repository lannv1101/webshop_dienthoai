package edu.poly.shop.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Customer;

import javax.mail.MessagingException;

public interface CustomerService {

	Customer getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Customer> entities);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Customer entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	long count();

	<S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Customer> S saveAndFlush(S entity);

	void flush();

	<S extends Customer> List<S> saveAll(Iterable<S> entities);

	Optional<Customer> findById(Long id);

	List<Customer> findAllById(Iterable<Long> ids);

	List<Customer> findAll(Sort sort);

	List<Customer> findAll();

	Page<Customer> findAll(Pageable pageable);

	<S extends Customer> S save(S entity);

	Page<Customer> findByNameContaining(String name, Pageable pageable);

	List<Customer> findByNameContaining(String name);

    void register(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException;

    void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    Optional<Customer> findByEmail(String email);

    boolean verify(String verificationCode);

    Customer login(String email, String password);

	Integer countCustomer();

}
