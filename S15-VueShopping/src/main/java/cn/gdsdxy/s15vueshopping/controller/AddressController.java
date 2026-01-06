package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.Address;
import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.AddressService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;
    @Autowired
    RedisTemplate redisTemplate;

    // 辅助方法：从Token获取当前登录用户的ID
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        if (token == null) return null;
        ShopUser shopUser = (ShopUser) redisTemplate.opsForValue().get(token);
        return shopUser != null ? shopUser.getUserId() : null;
    }

    // 获取地址列表
    @GetMapping("/list")
    public FwResult list(HttpServletRequest request) {
        Integer userId = getUserIdFromToken(request);
        if (userId == null) return FwResult.failedMsg("未登录");

        List<Address> list = addressService.getAddressList(userId);
        return FwResult.ok(list);
    }

    // 新增地址
    @PostMapping("/add")
    public FwResult add(@RequestBody Address address, HttpServletRequest request) {
        Integer userId = getUserIdFromToken(request);
        if (userId == null) return FwResult.failedMsg("未登录");

        address.setUserId(userId); // 确保地址绑定到当前用户
        addressService.addAddress(address);
        return FwResult.okMsg("地址添加成功");
    }

    // 修改地址
    @PostMapping("/update")
    public FwResult update(@RequestBody Address address, HttpServletRequest request) {
        Integer userId = getUserIdFromToken(request);
        if (userId == null) return FwResult.failedMsg("未登录");

        address.setUserId(userId);
        addressService.updateAddress(address);
        return FwResult.okMsg("地址修改成功");
    }

    // 删除地址
    @GetMapping("/delete")
    public FwResult delete(@RequestParam Integer addressId) {
        addressService.deleteAddress(addressId);
        return FwResult.okMsg("删除成功");
    }
}