package com.fjp.controller.pet;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.util.MinioUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
@Api("文件资源")
public class MinioController {

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @OperationLogAnnotation(operModul = "文件资源模块-上传文件",operType = "上传文件",operDesc = "上传文件")
    public String MinIOUpload(MultipartFile file) {
        if (file.isEmpty() || file.getSize() == 0) {
            return "文件为空";
        }
        try {
            if (!minioUtil.bucketExists("pet")) {
                minioUtil.makeBucket("pet");
            }

            String fileName = file.getOriginalFilename();
            String newName = "image/" + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();
            minioUtil.putObject("pet", newName, inputStream);
            inputStream.close();

            String url = minioUtil.getObjectUrl("pet", newName);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

}

