package cn.edu.neu.mgzmsys.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final String FILE_UPLOAD_DIRECTORY = "/mgzm/file_upload/";

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = FILE_UPLOAD_DIRECTORY + fileName;
                File destFile = new File(filePath);

                // 创建目录（如果不存在）
                File parentDirectory = destFile.getParentFile();
                if (!parentDirectory.exists()) {
                    parentDirectory.mkdirs();
                }

                // 保存文件
                file.transferTo(destFile);
                return "File uploaded successfully!";
            } catch (Exception e) {
                return "File upload failed: " + e.getMessage();
            }
        } else {
            return "File is empty!";
        }
    }
}
