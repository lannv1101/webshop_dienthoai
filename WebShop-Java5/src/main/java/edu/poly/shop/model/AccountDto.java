package edu.poly.shop.model;



import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
	@NotEmpty
	@Length(min = 5)
	private String username;
	@NotEmpty
	@Length(min = 5)
	private String password;
	
	private Short role;
	private Boolean isEdit= false;
}
