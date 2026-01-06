package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("addresses")
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer addressId;
    private Integer userId;
    private String recipientName;
    private String recipientPhone;
    private String addressDetail;
    private Integer isDefault;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    private Date createdAt;
    private Date updatedAt;
}