package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.Review;
import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.ReviewService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/addReview")
    public FwResult addReview(@RequestBody Review review, HttpServletRequest request) {
        String token = request.getHeader(JwtConstant.HEADER_NAME);
        ShopUser user = (ShopUser) redisTemplate.opsForValue().get(token);

        if (user == null) {
            return FwResult.failedMsg("请先登录");
        }

        review.setUserId(user.getUserId());
        boolean success = reviewService.addReview(review);
        return success ? FwResult.okMsg("评价成功") : FwResult.failedMsg("评价失败");
    }

    @GetMapping("/productReviews")
    public FwResult productReviews(@RequestParam("productId") int productId) {
        List<Review> list = reviewService.getReviewsByProductId(productId);
        return FwResult.ok(list);
    }
}