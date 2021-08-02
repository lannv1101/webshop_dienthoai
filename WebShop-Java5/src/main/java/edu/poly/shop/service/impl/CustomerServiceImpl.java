package edu.poly.shop.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.repository.CustomerRepository;
import edu.poly.shop.service.CustomerService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CustomerServiceImpl implements CustomerService {


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
			private JavaMailSender mailSender;



	CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
	
		this.customerRepository = customerRepository;
	}
	@Override
	public boolean verify(String verificationCode) {
		Customer customer = customerRepository.findByVerificationCode(verificationCode);

		if (customer == null || customer.isEnabled()) {
			return false;
		} else {
			customer.setVerification_code(null);
			customer.setEnabled(true);
			customerRepository.save(customer);

			return true;
		}

	}

	@Override
	public Customer login(String email, String password) {
		Optional<Customer> optExist = findByEmail(email);
		
		if (optExist.isPresent() && bCryptPasswordEncoder.matches(password,optExist.get().getPassword())) {
			optExist.get().setPassword("");
			return optExist.get();
		}
		return null;
	}
	@Override
	public void register(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String encodedPass = bCryptPasswordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPass);
		String randomcode = RandomString.make(64);
		customer.setVerification_code(randomcode);
		customer.setEnabled(false);


		customerRepository.save(customer);
		sendVerificationEmail(customer,siteURL);
		
	}

	@Override
	public void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException {

		String toAddress = customer.getEmail();
		String fromAddress = "Your email address";
		String senderName = "Your company name";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "Your company name.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("lannvpd03981@fpt.edu.vn", "TechShop");
		helper.setTo(customer.getEmail());
		helper.setSubject("Verify Email login to TechShop");

		content = content.replace("[[name]]", customer.getName());
		String verifyURL = siteURL + "/techshop/login/verify?code=" + customer.getVerification_code();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);
	}


	@Override
	public Optional<Customer> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	@Override
	public List<Customer> findByNameContaining(String name) {
		return customerRepository.findByNameContaining(name);
	}

	@Override
	public Page<Customer> findByNameContaining(String name, Pageable pageable) {
		return customerRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Customer> S save(S entity) {
		entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));


		return customerRepository.save(entity);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findAll(Sort sort) {
		return customerRepository.findAll(sort);
	}

	@Override
	public List<Customer> findAllById(Iterable<Long> ids) {
		return customerRepository.findAllById(ids);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
		return customerRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		customerRepository.flush();
	}

	@Override
	public <S extends Customer> S saveAndFlush(S entity) {
		return customerRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	@Override
	public <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities) {
		return customerRepository.saveAllAndFlush(entities);
	}

	@Override
	public long count() {
		return customerRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		customerRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		customerRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		customerRepository.deleteAllInBatch();
	}

	
	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		customerRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
	}

	@Override
	public Customer getById(Long id) {
		return customerRepository.getById(id);
	}

	@Override
	public Integer countCustomer() {
		return customerRepository.countCustomer();
	}
	
	
	
}
