package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.entity.Products;
import cn.gdsdxy.s15vueshopping.service.ProductsService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping("/list")
    public FwResult list(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Products> list = productsService.list();
        return FwResult.ok(list);
    }

    @GetMapping("/detail")
    public FwResult detail(@RequestParam("id") int id) {
        Products product = productsService.getById(id);
        if (product != null) {
            return FwResult.ok(product);
        } else {
            return FwResult.failedMsg("商品不存在");
        }
    }

    @GetMapping("/category")
    public FwResult getByCategory(@RequestParam("categoryId") int categoryId) {
        QueryWrapper<Products> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        return FwResult.ok(productsService.list(wrapper));
    }
}