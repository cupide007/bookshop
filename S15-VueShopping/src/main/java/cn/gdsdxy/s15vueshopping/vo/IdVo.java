package cn.gdsdxy.s15vueshopping.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//该类用了存放待删除的商品Id
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdVo {
    private Integer productId;//特别注意：这里的属性名必须跟products数据表的主键列名相同
}
