package cn.gdsdxy.s15vueshopping.controller;

import cn.gdsdxy.s15vueshopping.util.FwResult;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
public class UploadController {

    @PostMapping("/upload")
    public FwResult upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return FwResult.failedMsg("上传文件不能为空");
        }

        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");

            File uploadDir = new File(path.getAbsolutePath(), "static/images/");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String suffix = null;
            if (originalFilename != null) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFileName = UUID.randomUUID()+ suffix;

            File dest = new File(uploadDir.getAbsolutePath() + File.separator + newFileName);
            file.transferTo(dest);

            return FwResult.ok(newFileName);
        } catch (IOException e) {
            e.fillInStackTrace();
            return FwResult.failedMsg("文件上传失败");
        }
    }
}