package com.kh.helloffice.board.controller;

import com.kh.helloffice.board.entity.FileInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ImageController {

    private static String IMAGE_DIR = "C:/test/image/";

    @PostMapping("/image")
    public String uploadImage(MultipartFile image) throws Exception {
        String fileName = image.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf("."));

        String saveName = UUID.randomUUID() + fileExt;
        String savePath = IMAGE_DIR + saveName;
        image.transferTo(new File(savePath));

        String thumbnailPath = IMAGE_DIR + "s_" + saveName;
        File thumbnail = new File(thumbnailPath);
        Thumbnails.of(new File(savePath))
                .size(200,200)
                .toFile(thumbnail);
        return savePath;
    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String path) throws Exception {
        Resource resource = new FileSystemResource(path);
        if(!resource.exists()){{
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }}

        HttpHeaders header = new HttpHeaders();
        Path filePath = Paths.get(path);
        header.add("Content-Type", Files.probeContentType(filePath));
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @GetMapping("/thumbnail")
    @ResponseBody
    public byte[] getThumbnail(@RequestParam String path) throws Exception {
        InputStream is = new FileInputStream(IMAGE_DIR + path);
        byte[] imageByteArray = IOUtils.toByteArray(is);
        is.close();
        return imageByteArray;
    }
}
