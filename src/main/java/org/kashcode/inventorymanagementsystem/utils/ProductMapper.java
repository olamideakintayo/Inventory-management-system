package org.kashcode.inventorymanagementsystem.utils;

import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.dtos.requests.ProductRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;

public class ProductMapper {
    public Product toProductEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setReOrderThreshold(request.getReOrderThreshold());
        product.setQuantityInStock(request.getQuantityInStock());
        return product;
    }

    public ProductResponse toProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setReOrderThreshold(product.getReOrderThreshold());
        response.setQuantityInStock(product.getQuantityInStock());
        return response;
    }

}
