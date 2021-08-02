package edu.poly.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.shop.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByNameContaining(String name);
	Page<Customer> findByNameContaining(String name, Pageable pageable);
	Optional<Customer> findByEmail(String email);
	@Query("select count(c.customerId) from Customer c")
	Integer countCustomer();
	@Query("SELECT u FROM Customer u WHERE u.verification_code = ?1")
	public Customer findByVerificationCode(String code);


}
