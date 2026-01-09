package cn.gdsdxy.s15vueshopping.controller;


import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.ShopUserService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.util.UUID;

@RestController
public class ShopUserController {
    @Autowired
    ShopUserService shopUserService;
    @Autowired
    RedisTemplate redisTemplate;
    @PostMapping("/login")
    public FwResult login(@RequestBody UserVo userVo){
        System.out.println(userVo);
        return shopUserService.login(userVo);

    }
    @PostMapping("/logout")
    public FwResult logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");//获取请求头中的token
        redisTemplate.delete(token);//把缓存中该token的键值对删掉，一旦删掉，就表示该用户已经注销
        return FwResult.okMsg("用户注销成功");
    }
    @PostMapping("/register")
    public FwResult register(@RequestBody ShopUser shopUser){
        return shopUserService.register(shopUser);
    }

    }


