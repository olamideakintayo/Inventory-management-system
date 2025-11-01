package org.kashcode.inventorymanagementsystem.controllers;

import lombok.RequiredArgsConstructor;
import org.kashcode.inventorymanagementsystem.dtos.requests.ProductRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;
import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping("/check-reorder/{productId}")
    public ResponseEntity<String> checkAndReorder(@PathVariable Long productId) {
        ProductResponse response = productService.getProductById(productId);

        Product product = new Product();
        product.setProductId(productId);
        product.setName(response.getName());
        product.setQuantityInStock(response.getQuantityInStock());
        product.setReOrderThreshold(response.getReOrderThreshold());

        productService.checkAndReorder(product);

        return ResponseEntity.ok("Reorder check completed for product ID " + productId);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }
    }