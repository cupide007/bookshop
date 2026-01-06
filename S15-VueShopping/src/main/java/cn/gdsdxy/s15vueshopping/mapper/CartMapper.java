package cn.gdsdxy.s15vueshopping.mapper;

import cn.gdsdxy.s15vueshopping.entity.Cart;
import cn.gdsdxy.s15vueshopping.vo.CartVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    @Select("SELECT cart_items.product_id,products.`name`,products.image_url,products.price,cart_items.quantity FROM cart_items " +
            "INNER JOIN products ON cart_items.product_id = products.product_id WHERE cart_items.user_id = #{userId} and cart_items.is_delete=0")
    List<CartVo> getCartByUserId(int userId);
}
