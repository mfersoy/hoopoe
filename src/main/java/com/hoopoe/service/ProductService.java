package com.hoopoe.service;

import com.hoopoe.domain.ImageFile;
import com.hoopoe.domain.Product;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.exception.ConflictException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageFileService imageFileService;

    @Autowired
    private ProductMapper productMapper;

    public void saveProduct(String imageId, ProductDTO productDTO){

        ImageFile imageFile = imageFileService.getImageById(imageId);

        Integer usedProductCount = productRepository.findProductByImageId(imageFile.getId()).size();


        if(usedProductCount>0){
            throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
        }

        Product product = productMapper.productDTOToProduct(productDTO);

        Set<ImageFile> setOfImFiles = new HashSet<>();
        setOfImFiles.add(imageFile);
        product.setImage(setOfImFiles);

        productRepository.save(product);
    }



}
