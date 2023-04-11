package com.hoopoe.mapper;

import com.hoopoe.domain.ImageFile;
import com.hoopoe.domain.Product;
import com.hoopoe.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "image", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> map(List<Product> products);

    @Mapping(source = "image", target = "image", qualifiedByName = "getImageAsString")
    ProductDTO productToProductDTO(Product product);

    @Named("getImageAsString")
    public static Set<String> getImageIds(Set<ImageFile> imageFiles) {
        Set<String> imgs = new HashSet<>();
        imgs = imageFiles.stream().map(imFile -> imFile.getId().toString()).collect(Collectors.toSet());
        return imgs;
    }

}
