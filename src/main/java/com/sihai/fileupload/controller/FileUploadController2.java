package com.sihai.fileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 单文件上传
 */
@RestController
public class FileUploadController2 {

    /**
     * 日期格式 作为目录拼接
     */
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    
    @PostMapping("/upload3")
    public void upload(MultipartFile file1, MultipartFile file2, HttpServletRequest request) {
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
        try {
            // 获取文件扩展名后缀, 对上传的文件重命名，避免文件重名
            String oldName1 = file1.getOriginalFilename();
            String newName1 = UUID.randomUUID().toString() + oldName1.substring(oldName1.lastIndexOf("."));
            // 文件保存
            file1.transferTo(new File(folder, newName1));
            // 生成上传文件的访问路径
            String s1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + format + newName1;
            System.out.println("s1 = " + s1);
            
            // 获取文件扩展名后缀, 对上传的文件重命名，避免文件重名
            String oldName2 = file2.getOriginalFilename();
            String newName2 = UUID.randomUUID().toString() + oldName2.substring(oldName2.lastIndexOf("."));
            // 文件保存
            file2.transferTo(new File(folder, newName2));
            // 生成上传文件的访问路径
            String s2 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + format + newName2;
            System.out.println("s2 = " + s2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
