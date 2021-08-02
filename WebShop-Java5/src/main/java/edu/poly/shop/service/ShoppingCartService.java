package edu.poly.shop.service;

import java.util.Collection;

import edu.poly.shop.domain.CartItem;
import edu.poly.shop.model.CartItemDto;

public interface ShoppingCartService {

	int getCount();

	double getAmout();

	void update(Long productId, int quantity);

	void clear();

	Collection<CartItemDto> getCartItems();

	void remove(Long productId);

	void add(CartItemDto item);

	

	


}
