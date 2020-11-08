package com.pm.paymentgateway.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto {

    private List<Product> products = new ArrayList<>();
    private Long orderId;
    private String userEmail;
}
