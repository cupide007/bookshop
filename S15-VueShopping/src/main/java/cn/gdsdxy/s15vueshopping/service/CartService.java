package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.Cart;
import cn.gdsdxy.s15vueshopping.mapper.CartMapper;
import cn.gdsdxy.s15vueshopping.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
   @Autowired
   CartMapper cartMapper;
   public List<CartVo> getCartByUserId(int userId){
     return cartMapper.getCartByUserId(userId);
   }
   public void addCart(int userId,int productId,int quantity){
      //先判断购物车表中当前用户待购买的商品列表中是否包含准备购买的商品，如果包含，则修改购物车列表中的待购买的商品数量，否则，往购物车中添加一条记录
      //1.判断购物车表中当前用户待购买的商品列表中是否包含准备购买的商品
      QueryWrapper<Cart> wrapper=new QueryWrapper<>();
      wrapper.eq("user_id",userId).eq("product_id",productId);
      List<Cart> cartList = cartMapper.selectList(wrapper);
      if(cartList.size()>0){//2.如果包含，则修改购物车列表中的待购买的商品数量
         UpdateWrapper<Cart> updateWrapper=new UpdateWrapper<>();
         updateWrapper.eq("user_id",userId).eq("product_id",productId)
                 .setSql("quantity=quantity+"+quantity);
         cartMapper.update(updateWrapper);
      }else{//否则，往购物车中添加一条记录
         Cart cart=new Cart();
         cart.setUserId(userId);
         cart.setProductId(productId);
         cart.setQuantity(quantity);
         cartMapper.insert(cart);//往数据表中添加一条记录
      }
   }

    // 更新购物车商品数量
    public void updateQuantity(int userId, int productId, int quantity) {
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId).eq("product_id", productId)
                .set("quantity", quantity);
        cartMapper.update(null, updateWrapper);
    }

    // 删除购物车中的某件商品
    public void deleteCartItem(int userId, int productId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("product_id", productId);
        cartMapper.delete(wrapper);
    }
}
