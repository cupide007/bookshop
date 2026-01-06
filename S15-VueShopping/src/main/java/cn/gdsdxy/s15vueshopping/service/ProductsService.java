package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.Products;
import cn.gdsdxy.s15vueshopping.mapper.ProductsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    ProductsMapper productsMapper;

    public List<Products> list() {
        return productsMapper.selectList(null);
    }

    public Products getById(Integer id) {
        return productsMapper.selectById(id);
    }

    public List<Products> list(QueryWrapper<Products> wrapper) {
        return productsMapper.selectList(wrapper);
    }

    public void updateById(Products product) {
        productsMapper.updateById(product);
    }
}