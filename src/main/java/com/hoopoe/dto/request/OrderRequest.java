package com.hoopoe.dto.request;
import com.hoopoe.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    public void addProductToSet(Product product){
        products.add(product);
    }


    @NotBlank(message = "Please provide a table")
    private String table;

    @NotBlank(message = "Please provide your product")
    private Set<Product> products;
}
