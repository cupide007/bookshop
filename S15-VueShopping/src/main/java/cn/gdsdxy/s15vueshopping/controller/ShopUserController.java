package cn.gdsdxy.s15vueshopping.controller;


import cn.gdsdxy.s15vueshopping.entity.ShopUser;
import cn.gdsdxy.s15vueshopping.service.ShopUserService;
import cn.gdsdxy.s15vueshopping.util.FwResult;
import cn.gdsdxy.s15vueshopping.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.util.UUID;

@RestController
public class ShopUserController {
    @Autowired
    ShopUserService shopUserService;
    @Autowired
    RedisTemplate redisTemplate;
    @PostMapping("/login")
    public FwResult login(@RequestBody UserVo userVo){
        System.out.println(userVo);
        return shopUserService.login(userVo);

    }
    @PostMapping("/logout")
    public FwResult logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");//获取请求头中的token
        redisTemplate.delete(token);//把缓存中该token的键值对删掉，一旦删掉，就表示该用户已经注销
        return FwResult.okMsg("用户注销成功");
    }
    @PostMapping("/register")
    public FwResult register(@RequestBody ShopUser shopUser){
        return shopUserService.register(shopUser);
    }

        @PostMapping("/register/uploadFile")
        public FwResult uploadFile(MultipartFile file) {
            // 默认文件上传成功，并返回状态信息
//        model.addAttribute("uploadStatus", "上传成功！");
            // 获取文件名以及后缀名
            String fileName = file.getOriginalFilename();
            // 重新生成文件名（根据具体情况生成对应文件名）
            fileName = UUID.randomUUID()+"_"+fileName;

            String dirPath=System.getProperty("user.dir")+"/resource/images/";//前面的方法用来获取当前项目的根目录，后面不包含\
            try {
                //通过下面的代码处理中文路径的问题
                dirPath = URLDecoder.decode(dirPath, "utf-8");//处理空格转化为%20

                File filePath = new File(dirPath);
                if(!filePath.exists()){
                    filePath.mkdirs();
                }

                file.transferTo(new File(dirPath+fileName));

            } catch (Exception e) {
                e.printStackTrace();
                return FwResult.failedMsg("上传失败");
                // 上传失败，返回失败信息
//                model.addAttribute("uploadStatus","上传失败： "+e.getMessage());
            }

            return FwResult.ok(fileName,"上传成功");//将上传后的文件名传给前端页面
        }

    }


