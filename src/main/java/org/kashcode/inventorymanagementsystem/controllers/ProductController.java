package org.kashcode.inventorymanagementsystem.controllers;


import jakarta.validation.Valid;
import org.kashcode.inventorymanagementsystem.dtos.requests.ProductRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;
import org.kashcode.inventorymanagementsystem.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product) {
        ProductResponse savedProduct = productService.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


}
