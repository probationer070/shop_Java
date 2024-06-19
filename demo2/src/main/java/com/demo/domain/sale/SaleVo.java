//package com.demo.domain.sale;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.demo.domain.saleItem.SaleItemVo;
//import com.demo.domain.user.UserVo;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Table(name="Sale")
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Entity
//public class SaleVo {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="seller_id")
//    private UserVo seller; // 판매자
//
//    @OneToMany(mappedBy = "sale")
//    private List<SaleItemVo> saleItems = new ArrayList<>();
//
//    private int totalCount; // 총 판매 개수
//
//    public static SaleVo createSale(UserVo seller) {
//    	SaleVo sale = new SaleVo();
//        sale.setSeller(seller);
//        sale.setTotalCount(0);
//        return sale;
//    }
//}
