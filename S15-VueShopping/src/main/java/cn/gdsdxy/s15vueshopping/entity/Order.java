package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer orderId;
    private Integer userId;
    private String orderNumber;
    private BigDecimal totalAmount;
    private String status;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    private Date createdAt;
    private Date updatedAt;

    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    @TableField(exist = false)
    private List<OrderItem> items;
}