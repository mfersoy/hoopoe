package com.hoopoe.service;

import com.hoopoe.domain.ImageFile;
import com.hoopoe.domain.Product;
import com.hoopoe.dto.ProductDTO;
import com.hoopoe.exception.BadRequestException;
import com.hoopoe.exception.ConflictException;
import com.hoopoe.exception.ResourceNotFoundException;
import com.hoopoe.exception.message.ErrorMessage;
import com.hoopoe.mapper.ProductMapper;
import com.hoopoe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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


    public List<ProductDTO> getAllProducts(){
        List<Product> productList= productRepository.findAll();
        return productMapper.map(productList);
    }

    public ProductDTO findById(Long id){
        Product product = productRepository.findProductById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE)));
        return  productMapper.productToProductDTO(product);

    }
    public Product getProduct(Long id) {
        Product product = productRepository.findProductById(id).orElseThrow(() -> new
                ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        return product;
    }
    public void updatedProduct(Long id , String imageId, ProductDTO productDTO){
        Product product = getProduct(id);
        if(product.getBuiltIn()){
            throw  new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        ImageFile imageFile = imageFileService.getImageById(imageId);

        List<Product> productList = productRepository.findProductByImageId(imageFile.getId());

        for(Product p: productList){
            if(product.getId().longValue()!=p.getId().longValue()){
                throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
            }
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.getImage().add(imageFile);

        productRepository.save(product);

    }



}
