package cn.edu.neu.mgzmsys.controller;

import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/file")
public class FileController {

    public static final String FILE_UPLOAD_DIRECTORY = "/mgzm/file_upload/message_files";

    @PostMapping(value = "/upload", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> uploadFile(@RequestBody String messageId, @RequestBody MultipartFile file) {
        try {
            if ( file.isEmpty() ) {
                throw new NullPointerException();
            }
            String fileName = file.getOriginalFilename();

            // 保存文件的路径: /mgzm/file_upload/message_files/{messageId}/{fileName}
            String filePath = FILE_UPLOAD_DIRECTORY + messageId + "/" + fileName;

            File destFile = new File(filePath);

            // 创建目录（如果不存在）
            File parentDirectory = destFile.getParentFile();
            if ( !parentDirectory.exists() ) {
                parentDirectory.mkdirs();
            }
            // 保存文件
            file.transferTo(destFile);
            return HttpResponseEntity.UPDATE_SUCCESS.toResponseEntity();
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }

    @GetMapping(value = "/download/{filePath}")
    public ResponseEntity<HttpResponseEntity> downloadFile(@PathVariable("filePath") String filePath) {
        // 解析文件保存地址
        String[] pathArray = filePath.split("/");
        String fileName = pathArray[pathArray.length - 1];

        // 创建文件对象
        File file = new File(filePath);

        // 文件不存在
        if ( !file.exists() ) {
            return HttpResponseEntity.GET_FAIL.toResponseEntity();
        }

        try {
            // 读取文件内容
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            long fileSize = file.length();

//            // 设置响应头部信息
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", fileName);
//            headers.setContentLength(fileSize);

            // 返回响应
            return new HttpResponseEntity().get(fileBytes).toResponseEntity();
        } catch ( Exception e ) {
            // 如果发生异常，可以返回适当的错误响应
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}
