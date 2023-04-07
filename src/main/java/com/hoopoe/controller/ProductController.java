package com.hoopoe.controller;

import com.hoopoe.dto.ProductDTO;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HResponse> saveProduct(@PathVariable String imageId, @RequestBody @Valid ProductDTO productDTO){

     //   productService.

    return null;

    }




}
