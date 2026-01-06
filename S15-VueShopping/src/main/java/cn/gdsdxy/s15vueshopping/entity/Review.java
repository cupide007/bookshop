package cn.gdsdxy.s15vueshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("reviews")
public class Review {
    @TableId(type = IdType.AUTO)
    private Integer reviewId;
    private Integer userId;
    private Integer productId;
    private Integer rating;
    private String comment;
    private String imageUrl;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    private Date createdAt;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String userAvatar;
}