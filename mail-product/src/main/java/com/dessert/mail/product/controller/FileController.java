package com.dessert.mail.product.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {
    @PostMapping("uploadImg")
    public String uploadImg(@RequestPart(value = "file") MultipartFile file) throws IOException {
        // 定义存储图片的地址
        String folder = "D:/allworks/graduation-project/dessert-mail/dessert-mail-ui/src/assets/images";
        // 文件夹
        File imgFolder = new File(folder);
        // 获取文件名
        String fname = file.getOriginalFilename();
        // 获取文件后缀
        String ext = "." + fname.substring(fname.lastIndexOf(".") + 1);
        // 获取时间字符串
        String dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        // 生成新的文件名
        String newFileName = dateTimeFormatter + UUID.randomUUID().toString().replaceAll("-", "") + ext;
        // 文件在本机的全路径
        File filePath = new File(imgFolder, newFileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        try {
            file.transferTo(filePath);
            // 返回文件名
            return filePath.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
