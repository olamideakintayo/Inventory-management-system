package org.kashcode.inventorymanagementsystem.dtos.responses;

import lombok.Data;

@Data
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Integer reorderThreshold;
    private Integer quantityInStock;
}