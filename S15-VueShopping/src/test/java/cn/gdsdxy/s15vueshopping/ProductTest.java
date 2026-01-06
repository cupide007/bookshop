package cn.gdsdxy.s15vueshopping;

import cn.gdsdxy.s15vueshopping.entity.Products;
import cn.gdsdxy.s15vueshopping.mapper.ProductsMapper;
import cn.gdsdxy.s15vueshopping.vo.IdVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductTest {
    @Autowired
    ProductsMapper productsMapper;
    @Test
    void delete(){
        List<IdVo> idList=new ArrayList<>();
//        IdVo idVo=new IdVo();
//        idVo.setProductId(1);
//        idList.add(idvo);
        idList.add(new IdVo(11));//该语句等价于上面三个语句
        idList.add(new IdVo(12));
        idList.add(new IdVo(13));
        int i = productsMapper.deleteBatchIds(idList);
        System.out.println("删除了"+i+"条记录");
    }
}
