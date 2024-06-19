package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.domain.cartItem.CartItemVo;
import com.demo.domain.order.OrderRepo;
import com.demo.domain.order.OrderVo;
import com.demo.domain.orderItem.OrderItem;
import com.demo.domain.orderItem.OrderItemRepo;
import com.demo.domain.user.UserVo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private OrderItemRepo orderItemRepo;

	
	@Transactional
	public OrderItem addCartOrder(int itemId, UserVo user, CartItemVo cartItem) {

        OrderItem orderItem = OrderItem.createOrderItem(itemId, user, cartItem);
        log.info("orderItem +++++> "+orderItem);
        orderItemRepo.save(orderItem);

        return orderItem;
	}

	public void addOrder(UserVo user, List<OrderItem> orderItemList) {
		OrderVo userOrder = OrderVo.createOrder(user, orderItemList);

        orderRepo.save(userOrder);
		
	}

	
	

}
