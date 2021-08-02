package edu.poly.shop.global;

import java.util.ArrayList;
import java.util.List;

import edu.poly.shop.domain.Product;

public class GlobalData {

	public static List<Product> cart;
	
	static {
		cart= new ArrayList<Product>();
	}
}
