package com.hoopoe.mapper;

import com.hoopoe.domain.Product;
import com.hoopoe.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "image", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);
}
