package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.dtos.requests.ProductRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest product);

    void checkAndReorder(Product product);

    void updateStock(Long productId, int newQuantity);

    List<ProductResponse> getAllProducts();


}
