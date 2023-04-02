package com.hoopoe.service;

import com.hoopoe.domain.ImageData;
import com.hoopoe.domain.ImageFile;
import com.hoopoe.dto.ImageFileDTO;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public ImageFile getImageById (String imageId){
        ImageFile imageFile = imageFileRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, imageId)));
        return imageFile;
    }

    public List<ImageFileDTO> getAllImages (){
        List<ImageFile> imageFiles = imageFileRepository.findAll();

       return imageFiles.stream().map(imageFile -> {
            String imageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/download")
                    .path(imageFile.getId()).toUriString();
            return new ImageFileDTO(imageFile.getName(),imageUri,imageFile.getType(),imageFile.getLength());
        }).collect(Collectors.toList());


    }
}
