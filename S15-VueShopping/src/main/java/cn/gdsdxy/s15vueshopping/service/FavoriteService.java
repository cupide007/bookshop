package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.Favorite;
import cn.gdsdxy.s15vueshopping.entity.Products;
import cn.gdsdxy.s15vueshopping.mapper.FavoriteMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    ProductsService productsService;

    public boolean addFavorite(int userId, int productId) {
        if (isFavorite(userId, productId)) {
            return true;
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favorite.setCreatedAt(new Date());
        return favoriteMapper.insert(favorite) > 0;
    }

    public boolean removeFavorite(int userId, int productId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("product_id", productId);
        return favoriteMapper.delete(wrapper) > 0;
    }

    public boolean isFavorite(int userId, int productId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("product_id", productId);
        return favoriteMapper.selectCount(wrapper) > 0;
    }

    public List<Favorite> getMyFavorites(int userId) {
        QueryWrapper<Favorite> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("created_at");
        List<Favorite> list = favoriteMapper.selectList(wrapper);

        for (Favorite fav : list) {
            Products p = productsService.getById(fav.getProductId());
            fav.setProduct(p);
        }
        return list;
    }
}