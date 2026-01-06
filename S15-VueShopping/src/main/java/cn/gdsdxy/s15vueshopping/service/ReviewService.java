package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.Review;
import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.mapper.ReviewMapper;
import cn.gdsdxy.s15vueshopping.mapper.ShopUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    ShopUserMapper userMapper;

    public boolean addReview(Review review) {
        review.setCreatedAt(new Date());
        return reviewMapper.insert(review) > 0;
    }

    public List<Review> getReviewsByProductId(int productId) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", productId).orderByDesc("created_at");
        List<Review> list = reviewMapper.selectList(wrapper);

        for (Review r : list) {
            ShopUser user = userMapper.selectById(r.getUserId());
            if (user != null) {
                r.setUsername(user.getUsername());
                r.setUserAvatar(user.getImageUrl());
            } else {
                r.setUsername("匿名用户");
            }
        }
        return list;
    }
}