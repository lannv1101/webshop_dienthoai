package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Account;

public interface AccountService {

	<S extends Account> List<S> findAll(Example<S> example, Sort sort);

	<S extends Account> List<S> findAll(Example<S> example);

	Account getById(String id);

	void deleteAll();

	void deleteAll(Iterable<? extends Account> entities);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends String> ids);

	void delete(Account entity);

	void deleteAllByIdInBatch(Iterable<String> ids);

	void deleteById(String id);

	long count();

	<S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

	boolean existsById(String id);

	void flush();

	<S extends Account> List<S> saveAll(Iterable<S> entities);

	Optional<Account> findById(String id);

	List<Account> findAll();

	Page<Account> findAll(Pageable pageable);

	<S extends Account> S save(S entity);

	Page<Account> findByUsernameContaining(String username, Pageable pageable);

	List<Account> findByUsernameContaining(String username);

}
