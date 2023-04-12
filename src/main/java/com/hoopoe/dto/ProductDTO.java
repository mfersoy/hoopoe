package com.hoopoe.dto;

import com.hoopoe.domain.Category;
import com.hoopoe.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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

    private Set<String> categories;

    public void setCategories(Set<Category> categories){
        Set<String> categoryStr = new HashSet<>();

        categories.forEach(c->{
            categoryStr.add(c.getType().getName());
        });
        this.categories = categoryStr;

    }
}
