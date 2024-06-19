//package com.demo.domain.saleItem;
//
//import java.time.LocalDate;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import com.demo.domain.Item.ItemVo;
//import com.demo.domain.cartItem.CartItemVo;
//import com.demo.domain.orderItem.OrderItem;
//import com.demo.domain.sale.SaleVo;
//import com.demo.domain.user.UserVo;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.PrePersist;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Table(name="SaleItem")
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//public class SaleItemVo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="sale_id")
//    private SaleVo sale;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "seller_id")
//    private UserVo seller; // 판매자
//
//    private int itemId; // 주문 상품 번호
//    private String itemName; // 주문 상품 이름
//    private int itemPrice; // 주문 상품 가격
//    private int itemCount; // 주문 상품 수량
//    private int itemTotalPrice; // 가격*수량
//
//    @OneToOne(mappedBy = "saleItem")
//    private OrderItem orderItem; // 판매 상품에 매핑되는 주문 상품
//
//    private int isCancel; // 판매 취소 여부 (0:판매완료 / 1:판매취소)
//
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    private LocalDate createDate; // 날짜
//
//    @PrePersist
//    public void createDate(){
//        this.createDate = LocalDate.now();
//    }
//
//    // 장바구니 전체 주문
//    public static SaleItemVo createSaleItem(int itemId, SaleVo sale, UserVo seller, CartItemVo cartItem) {
//        SaleItemVo saleItem = new SaleItemVo();
//        saleItem.setItemId(itemId);
//        saleItem.setSale(sale);
//        saleItem.setSeller(seller);
//        saleItem.setItemName(cartItem.getItem().getName());
//        saleItem.setItemPrice(cartItem.getItem().getItem_price());
//        saleItem.setItemCount(cartItem.getCount());
//        saleItem.setItemTotalPrice(cartItem.getItem().getItem_price()*cartItem.getCount());
//        return saleItem;
//    }
//
//    // 상품 개별 주문
//    public static SaleItemVo createSaleItem(int itemId, SaleVo sale, UserVo seller, ItemVo item, int count) {
//        SaleItemVo saleItem = new SaleItemVo();
//        saleItem.setItemId(itemId);
//        saleItem.setSale(sale);
//        saleItem.setSeller(seller);
//        saleItem.setItemName(item.getName());
//        saleItem.setItemPrice(item.getItem_price());
//        saleItem.setItemCount(count);
//        saleItem.setItemTotalPrice(item.getItem_price()*count);
//        return saleItem;
//    }
//}