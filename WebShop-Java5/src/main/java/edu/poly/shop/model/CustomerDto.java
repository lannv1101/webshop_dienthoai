package edu.poly.shop.model;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto   {

	private Long customerId;

	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	private String password;
	@NotEmpty
	@Length(min = 9)
	private String phone;
	@NotEmpty
	@Length(min = 5)
	private String address;
	private Date registeredDate = new Date();
	private short status;
	
	private Boolean isEdit = false;

	
	
}
