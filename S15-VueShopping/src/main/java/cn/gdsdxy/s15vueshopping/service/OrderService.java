package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.*;
import cn.gdsdxy.s15vueshopping.mapper.AddressMapper;
import cn.gdsdxy.s15vueshopping.mapper.CartMapper;
import cn.gdsdxy.s15vueshopping.mapper.OrderItemMapper;
import cn.gdsdxy.s15vueshopping.mapper.OrderMapper;
import cn.gdsdxy.s15vueshopping.vo.OrderVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    ProductsService productsService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Transactional
    public String createOrder(List<OrderVo> orderVoList, int userId, int addressId) {
        
        Address address = addressMapper.selectById(addressId);
        if (address == null) {
            throw new RuntimeException("收货地址不存在");
        }

        String orderNumber = generateOrderNumber();

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(userId);
        order.setStatus("pending");
        order.setCreatedAt(new Date());

        order.setReceiverName(address.getRecipientName());
        order.setReceiverPhone(address.getRecipientPhone());
        order.setReceiverAddress(address.getAddressDetail());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderVo vo : orderVoList) {
            Products product = productsService.getById(vo.getProductId());
            if (product != null) {
                BigDecimal price = product.getPrice();
                BigDecimal quantity = BigDecimal.valueOf(vo.getQuantity());
                totalAmount = totalAmount.add(price.multiply(quantity));
            }
        }
        order.setTotalAmount(totalAmount);
        
        orderMapper.insert(order);
        
        for (OrderVo vo : orderVoList) {
            Products product = productsService.getById(vo.getProductId());
            if (product != null) {
                
                if (product.getStock() < vo.getQuantity()) {
                    
                    throw new RuntimeException("商品 [" + product.getName() + "] 库存不足，仅剩 " + product.getStock() + " 件");
                }

                product.setStock(product.getStock() - vo.getQuantity());
                productsService.updateById(product); 

                OrderItem item = new OrderItem();
                item.setOrderId(order.getOrderId());
                item.setProductId(product.getProductId());
                item.setProductName(product.getName());
                item.setProductImage(product.getImageUrl());
                item.setPrice(product.getPrice());
                item.setQuantity(vo.getQuantity());

                orderItemMapper.insert(item);
            }
        }

        for (OrderVo vo : orderVoList) {
            QueryWrapper<Cart> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId).eq("product_id", vo.getProductId());
            cartMapper.delete(wrapper);
        }

        return orderNumber;
    }

    public int payOrder(String orderNumber) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", "paid");
        updateWrapper.eq("order_number", orderNumber).eq("status", "pending");
        return orderMapper.update(null, updateWrapper);
    }

    public List<Order> getOrdersByUserId(int userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).orderByDesc("created_at");
        List<Order> orders = orderMapper.selectList(queryWrapper);

        for (Order order : orders) {
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getOrderId());
            List<OrderItem> items = orderItemMapper.selectList(itemWrapper);
            order.setItems(items);
        }
        return orders;
    }

    private String generateOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());

        String key = "order:seq:" + dateStr;

        Long seq = redisTemplate.opsForValue().increment(key);

        if (seq != null && seq == 1) {
            redisTemplate.expire(key, 24, TimeUnit.HOURS);
        }

        return dateStr + String.format("%04d", seq);
    }
}