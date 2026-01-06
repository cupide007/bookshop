package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("cart_items")
public class Cart {
    @TableId(type = IdType.AUTO)
    private Integer cartId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    @TableLogic(value = "0",delval = "1")
    private Integer isDelete;
    private Date createdAt;
    private Date updatedAt;
}
