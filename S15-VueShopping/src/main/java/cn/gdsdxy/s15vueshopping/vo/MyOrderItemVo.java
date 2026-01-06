package cn.gdsdxy.s15vueshopping.vo;

import lombok.Data;

@Data
public class MyOrderItemVo {
    private Integer productId;
    private String productName;
    private String productImage;
    private Double price;
    private Integer quantity;
}