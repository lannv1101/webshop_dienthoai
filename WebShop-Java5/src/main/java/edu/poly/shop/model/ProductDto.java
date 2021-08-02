package edu.poly.shop.model;


import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import edu.poly.shop.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable  {
	
	
	private Long productId;
	
	@NotEmpty
	@Length(min= 5)
	private String name;
	@NotNull
	@DecimalMin("0")
	private int quantity;
	
	@DecimalMin("0.0")
	private double unitPrince;
	
	private String image;
	private MultipartFile imageFile;
	@NotEmpty
	@Length(min= 5)
	private String description;
	
	@DecimalMin("0.0")
	private double discount;
	private Date enteredDate = new Date();
	private short status;
	private Long categoryId; 
	
	private Boolean isEdit= false;
	
}
