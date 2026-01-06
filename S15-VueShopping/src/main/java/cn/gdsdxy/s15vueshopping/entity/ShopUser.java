package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class ShopUser {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String imageUrl;
    @TableLogic(value = "0",delval = "1")
    private Integer isDelete;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
}

