package com.sisterside.ssnewsapi.controller;

import com.sisterside.ssnewsapi.service.AmazonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/postmedia")
public class PostMediaController {

    private AmazonClient amazonClient;

    @PostMapping
    private ResponseEntity postMedia(@RequestPart(value = "file") MultipartFile file) {
        String url = this.amazonClient.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).header("Url", url).build();
    }

    @DeleteMapping(value = "/{fileUrl}")
    private ResponseEntity deleteMedia(@RequestParam String fileUrl) {
        this.amazonClient.deleteFileFromS3Bucket(fileUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
