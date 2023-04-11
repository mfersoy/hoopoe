package com.hoopoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {


    private  Long id;

    @NotBlank(message = "Please provide car door info")
    private  String name;

    @NotBlank(message = "Please provide description")
    private  String description;

    @NotNull(message = "Please provide price of product ")
    private Double price;

    private Boolean builtIn;

    private Set<String> image;
}
