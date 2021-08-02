package edu.poly.shop.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.poly.shop.domain.CartItem;
import edu.poly.shop.model.CartItemDto;
import edu.poly.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

private Map<Long, CartItemDto> map = new HashMap<Long, CartItemDto>();
	
	
	
	
	@Override
	public void add(CartItemDto item) {
		CartItemDto existedItem = map.get(item.getProductId());
		
		if (existedItem !=null) {
			existedItem.setQuantity(item.getQuantity());
			;
		}else {
			map.put(item.getProductId(), item);
		}
	}
	
	
	
	
	
	
	@Override
	public void remove(Long productId) {
		map.remove(productId);
	}
	
	
	
	
	
	@Override
	public Collection<CartItemDto> getCartItems(){
		return map.values();
	}
	
	
	
	
	@Override
	public void clear() {
		map.clear();
	}
	
	
	
//		
	@Override
	public void update(Long productId, int quantity) {
		CartItemDto item = map.get(productId);
		
		item.setQuantity(quantity);
		
		if (item.getQuantity()<=0) {
			map.remove(productId);
		}
	}
	
	

	@Override
	public double getAmout() {
		return map.values().stream().mapToDouble(item->item.getQuantity()* item.getUnitPrince()).sum();
	}
	
	
	
	@Override
	public int getCount() {
		
		if (map.isEmpty()) {
			return 0;
		}
		return map.values().size();
		
	}


}
