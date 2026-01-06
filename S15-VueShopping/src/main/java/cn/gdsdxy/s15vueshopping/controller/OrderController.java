package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.Order;
import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.OrderService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import cn.gdsdxy.s15vueshopping.vo.OrderVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/order")
    public FwResult createOrder(@RequestBody List<OrderVo> orderVoList,
                                @RequestParam(value = "addressId", required = false, defaultValue = "1") int addressId,
                                HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);

        if (shopUser == null) {
            return FwResult.failedMsg("登录已过期");
        }

        try {
            String orderNumber = orderService.createOrder(orderVoList, shopUser.getUserId(), addressId);
            return FwResult.ok(orderNumber);
        } catch (Exception e) {
            return FwResult.failedMsg("下单失败：" + e.getMessage());
        }
    }

    @PostMapping("/payOrder")
    public FwResult payOrder(@RequestParam("orderNumber") String orderNumber) {
        int result = orderService.payOrder(orderNumber);
        if (result > 0) {
            return FwResult.okMsg("支付成功");
        } else {
            return FwResult.failedMsg("支付失败或订单状态异常");
        }
    }

    @GetMapping("/myOrders")
    public FwResult myOrders(HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);

        if (shopUser == null) {
            return FwResult.failedMsg("未登录");
        }

        List<Order> orders = orderService.getOrdersByUserId(shopUser.getUserId());
        return FwResult.ok(orders);
    }
}