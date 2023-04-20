package org.example.controller;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import org.exemple.data.request.FileDTO;
import org.exemple.service.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestPart("file") MultipartFile multipartFile, FileDTO file) throws IOException {
        Ulid ulid = UlidCreator.getUlid();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        //user.setPhotos(fileName);

        String uploadDir = "user-photos/"+ ulid.toString()+ file.getNombre();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return new ResponseEntity<>("Informacion", HttpStatus.OK);
    }
}
