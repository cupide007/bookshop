package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.Favorite;
import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.FavoriteService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/addFavorite")
    public FwResult addFavorite(@RequestParam("productId") int productId, HttpServletRequest request) {
        ShopUser user = getUser(request);
        if (user == null) return FwResult.failedMsg("请先登录");

        boolean success = favoriteService.addFavorite(user.getUserId(), productId);
        return success ? FwResult.okMsg("收藏成功") : FwResult.failedMsg("收藏失败");
    }

    @GetMapping("/removeFavorite")
    public FwResult removeFavorite(@RequestParam("productId") int productId, HttpServletRequest request) {
        ShopUser user = getUser(request);
        if (user == null) return FwResult.failedMsg("请先登录");

        boolean success = favoriteService.removeFavorite(user.getUserId(), productId);
        return success ? FwResult.okMsg("已取消收藏") : FwResult.failedMsg("操作失败");
    }

    @GetMapping("/isFavorite")
    public FwResult isFavorite(@RequestParam("productId") int productId, HttpServletRequest request) {
        ShopUser user = getUser(request);
        if (user == null) return FwResult.ok(false);

        boolean isFav = favoriteService.isFavorite(user.getUserId(), productId);
        return FwResult.ok(isFav);
    }

    @GetMapping("/myFavorites")
    public FwResult myFavorites(HttpServletRequest request) {
        ShopUser user = getUser(request);
        if (user == null) return FwResult.failedMsg("请先登录");

        List<Favorite> list = favoriteService.getMyFavorites(user.getUserId());
        return FwResult.ok(list);
    }

    private ShopUser getUser(HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        if (token == null) return null;
        return (ShopUser) redisTemplate.opsForValue().get(token);
    }
}