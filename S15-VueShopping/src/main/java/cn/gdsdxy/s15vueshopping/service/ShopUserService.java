package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.mapper.ShopUserMapper;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.util.JwtUtil;
import cn.gdsdxy.s15vueshopping.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class ShopUserService {
    @Autowired
    ShopUserMapper shopUserMapper;
    @Autowired
    RedisTemplate redisTemplate;
    public FwResult login(UserVo userVo){
        QueryWrapper<ShopUser> wrapper=new QueryWrapper<>();
        wrapper.eq("username",userVo.getUsername());
        ShopUser shopUser=shopUserMapper.selectOne(wrapper);
        if(shopUser==null){
            return FwResult.failedMsg("登录的用户不存在");
        }else{
            String password = userVo.getPassword();
            String encodePassword=shopUser.getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(password,encodePassword)){
                String token = JwtUtil.createToken();
                redisTemplate.opsForValue().set(token,shopUser,20, TimeUnit.MINUTES);
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("token", token);
                resultMap.put("username", shopUser.getUsername()); // 把用户名也传回去
                resultMap.put("userId", shopUser.getUserId());
                return FwResult.ok(resultMap,"用户密码正确,登录成功");
            }else{
                return FwResult.failedMsg("用户密码错误");
            }
        }
    }

    public FwResult register(ShopUser shopUser){
        QueryWrapper<ShopUser> wrapper=new QueryWrapper<>();
        wrapper.eq("username",shopUser.getUsername());
        ShopUser User =shopUserMapper.selectOne(wrapper);
        if(User==null){
            String password = shopUser.getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            String encodePassword = bCryptPasswordEncoder.encode(password);
            shopUser.setPassword(encodePassword);
            shopUserMapper.insert(shopUser);
            return FwResult.okMsg("用户注册成功");
        }else {
            return FwResult.failedMsg("用户名已存在，注册失败");
        }
    }

}
