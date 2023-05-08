package com.openclassrooms.shopmanager.product;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductModel {

    private Long id;
    @NotBlank (message = "Name must not be blank")
    private String name;            // Required
    private String description;
    private String details;
    @NotBlank (message = "quantity must not be blank and greater than zero")
    @Digits(integer = 6, fraction = 2)
    @Min(value = 1)
    private String quantity;       // Required, Integer, Greater than zero
    @NotBlank (message = "price must not be blank and greater than 0")
    @Digits(integer = 6, fraction = 2)
    @Min(value = 1)
    private String price;          // Required, Numeric, Greater than zero

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
