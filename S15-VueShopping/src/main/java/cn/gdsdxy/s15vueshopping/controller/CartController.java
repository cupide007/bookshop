package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.CartService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import cn.gdsdxy.s15vueshopping.vo.CartVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//前端是Vue的话，这里用@RestController；前端是Thymeleaf的话，这里用@Controller
@RestController//RestController等价于Controller+ResponseBody
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    RedisTemplate redisTemplate;
//    @GetMapping("/cart")
//    public String cart(Model model){
//        int userId=1;
//        List<CartVo> cartVoList = cartService.getCartByUserId(userId);
//        model.addAttribute("cartVoList",cartVoList);
//        return "cart";
//    }
@GetMapping("/addCart")
public FwResult addCart(@RequestParam("productId") int productId, HttpServletRequest request){
    // 1. 获取Token
    String token = request.getHeader(JwtConstant.HEADER_NAME);

    // 2. 从Redis获取用户信息
    ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);

    // 3. 判空处理（防止token过期或未登录报错）
    if(shopUser == null) {
        return FwResult.failedMsg("登录已过期，请重新登录");
    }

    int userId = shopUser.getUserId(); // 获取真实的userId
    int quantity = 1; // 默认加入数量为1

    // 4. 业务逻辑
    cartService.addCart(userId, productId, quantity);
    return FwResult.okMsg("已添加购物车");
}
    @PostMapping("/myCart")
    public FwResult myCart( HttpServletRequest request){
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        //用户登录成功后，会把产生的token及对应的用户信息存在Redis缓存中，加入购物车操作时，前端要在请求头携带token向后端发送请求，
        //当后端接收到前端的请求后，先从请求头中获取token信息，再根据这个token信息从缓存中获取这个token对应的用户信息，包括userId。
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);
        int userId=shopUser.getUserId();
        List<CartVo> cartVoList = cartService.getCartByUserId(userId);
        return FwResult.ok(cartVoList);
    }

    // 修改购物车数量
    @GetMapping("/updateCart")
    public FwResult updateCart(@RequestParam("productId") int productId,
                               @RequestParam("quantity") int quantity,
                               HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);
        if (shopUser == null) return FwResult.failedMsg("请先登录");

        cartService.updateQuantity(shopUser.getUserId(), productId, quantity);
        return FwResult.okMsg("数量更新成功");
    }

    // 删除购物车商品
    @GetMapping("/deleteCart")
    public FwResult deleteCart(@RequestParam("productId") int productId,
                               HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);
        if (shopUser == null) return FwResult.failedMsg("请先登录");

        cartService.deleteCartItem(shopUser.getUserId(), productId);
        return FwResult.okMsg("商品已删除");
    }
}
