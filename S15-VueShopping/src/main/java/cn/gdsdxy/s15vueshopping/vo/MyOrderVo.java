package cn.gdsdxy.s15vueshopping.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MyOrderVo {
    private Integer orderId;
    private String orderNumber;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<MyOrderItemVo> items;
}