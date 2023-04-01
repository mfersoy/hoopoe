package com.hoopoe.service;

import com.hoopoe.domain.ImageData;
import com.hoopoe.domain.ImageFile;
import com.hoopoe.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class ImageFileService {

    @Autowired
    private ImageFileRepository imageFileRepository;

    public String saveImage(MultipartFile file){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        ImageFile imageFile;

        try{
            ImageData imageData = new ImageData(file.getBytes());
            imageFile = new ImageFile(fileName,file.getContentType(),imageData);
        }catch (IOException e){
            throw  new RuntimeException(e.getMessage());
        }

        imageFileRepository.save(imageFile);
        return imageFile.getId();


    }
}
