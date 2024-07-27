package com.shop.vo.orderItem;

import com.shop.vo.cartItem.CartItemVo;
import com.shop.vo.order.OrderVo;
import com.shop.vo.user.UserVo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_id")
    private OrderVo order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserVo user; // 구매자

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="item_id")
    //private Item item;

    private int itemId; // 주문 상품 번호
    private String itemName; // 주문 상품 이름
    private int itemPrice; // 주문 상품 가격
    private int itemCount; // 주문 상품 수량
    private int itemTotalPrice; // 가격*수량

    //@OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name="saleItem_id")
    //private SaleItem saleItem; // 주문상품에 매핑되는 판매상품

    private int isCancel; // 주문 취소 여부 (0:주문완료 / 1:주문취소)

    // 장바구니 전체 주문
    public static OrderItem createOrderItem(int itemId, UserVo user, CartItemVo cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(itemId);
        orderItem.setUser(user);
        orderItem.setItemName(cartItem.getItem().getName());
        orderItem.setItemPrice(cartItem.getItem().getItem_price());
        orderItem.setItemCount(cartItem.getCount());
        orderItem.setItemTotalPrice(cartItem.getItem().getItem_price()*cartItem.getCount());
        return orderItem;
    }
}
