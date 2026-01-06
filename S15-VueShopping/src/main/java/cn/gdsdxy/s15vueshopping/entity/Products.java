package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("products")
public class Products {
    @TableId(type = IdType.AUTO)
    private Integer productId;
    private Integer categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    private Date createdAt;
    private Date updatedAt;
}