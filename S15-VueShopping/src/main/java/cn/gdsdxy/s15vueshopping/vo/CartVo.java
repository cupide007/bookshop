package cn.gdsdxy.s15vueshopping.vo;

import lombok.Data;
//定义该实体类作用是存放SELECT cart_items.product_id,products.`name`,products.image_url,products.price,cart_items.quantity FROM cart_items
//INNER JOIN products ON cart_items.product_id = products.product_id WHERE cart_items.user_id = 1语句的执行结果，因为该实体类存放的是多表关联的查询结果,
//不是单表查询结果，所有实体类命名时在后面加上Vo,并且放在Vo包下。
@Data
public class CartVo {
    private Integer productId;
    private String name;
    private String imageUrl;
    private Double price;
    private Integer quantity;
}
