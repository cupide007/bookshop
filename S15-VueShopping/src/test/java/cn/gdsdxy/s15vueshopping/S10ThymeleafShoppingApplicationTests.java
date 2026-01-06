package cn.gdsdxy.s15vueshopping;

import cn.gdsdxy.s15vueshopping.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class S10ThymeleafShoppingApplicationTests {
    @Autowired
    CartService cartService;
    @Test
    void contextLoads() {
        int userId=1;
        int productId=3;
        int quantity=1;
        cartService.addCart(userId,productId,quantity);
    }

    @Test
    void getPassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode("123456");
        System.out.println(encodePassword);
        if (bCryptPasswordEncoder.matches("123456",encodePassword))
            System.out.println("该明文密码与密文密码匹配");
        else{
            System.out.println("该明文密码与密文密码不匹配");
        }

    }

}
