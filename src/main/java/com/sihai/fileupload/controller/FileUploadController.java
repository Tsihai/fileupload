package com.sihai.fileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 单文件上传
 */
@RestController
public class FileUploadController {
    /**
     * 日期格式 作为目录拼接
     */
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    
    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("/");
        // 并且在 uploadFile 文件夹中通过日期对上传的文件归类保存
        String format = sdf.format(new Date());
        String path = realPath + format;
        File folder = new File(path);
        // 如果文件夹不存在, 则创建文件夹
        if(!folder.exists()) {
            // 创建n层目录
            folder.mkdirs();
        }
        // 获取文件扩展名后缀, 对上传的文件重命名，避免文件重名
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            // 文件保存
            file.transferTo(new File(folder, newName));
            // 生成上传文件的访问路径
            String s = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + format + newName;
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
