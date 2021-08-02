package edu.poly.shop.model;



import java.io.Serializable;

import javax.validation.constraints.NotEmpty;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDto implements Serializable {
	
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	
	
	private Boolean rememberme= false;


}
