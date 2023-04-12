package com.hoopoe.controller;

import com.hoopoe.dto.ProductDTO;
import com.hoopoe.dto.response.HResponse;
import com.hoopoe.dto.response.ResponseMessage;
import com.hoopoe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

   @Autowired
   private ProductService productService;

    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HResponse> saveProduct(@PathVariable String imageId, @RequestBody @Valid ProductDTO productDTO){

        productService.saveProduct(imageId,productDTO);
        HResponse response = new HResponse(ResponseMessage.PRODUCT_SAVED_SUCCESSFULLY_MESSAGE,true);

         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("visitors/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }
    @GetMapping("visitors/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){

        ProductDTO productDTO = productService.findById(id);

        return ResponseEntity.ok(productDTO);

    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HResponse> updateProduct(@RequestParam("id") Long id, @RequestParam("imageId") String imageId, @RequestBody @Valid ProductDTO productDTO){

        productService.updatedProduct(id,imageId,productDTO);

        HResponse response = new HResponse(ResponseMessage.PRODUCT_UPDATE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HResponse> deleteCar(@PathVariable Long id){

        productService.removeById(id);
        HResponse response = new HResponse(ResponseMessage.PRODUCT_DELETE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<ProductDTO>> getAllProductsWithPage(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size,
                                                                   @RequestParam("sort") String prop,
                                                                   @RequestParam(value = "direction", required=false,
                                                                   defaultValue="DESC") Direction direction){

        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<ProductDTO> productDTOS = productService.findAllWithPage(pageable);
        return ResponseEntity.ok(productDTOS);
    }






}
