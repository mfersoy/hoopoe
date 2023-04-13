package com.hoopoe.dto.request;

import com.hoopoe.domain.Product;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Please provide a price")
    private  Double totalPrice;

    @NotBlank(message = "Please provide a table")
    private String table;

    @NotBlank(message = "Please provide your product")
    private List<Product> products;
}
