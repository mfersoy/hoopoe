package com.hoopoe.controller;

import com.hoopoe.dto.response.ImageSavedResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/files")
public class ImageFileController {

    @Autowired
    private ImageFileService imageFileService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("file") MultipartFile file){
        String imageId = imageFileService.saveImage(file);

        ImageSavedResponse response = new ImageSavedResponse(imageId, ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE, true);

        return ResponseEntity.ok(response);
    }
}
