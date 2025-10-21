package org.kashcode.inventorymanagementsystem.dtos.requests;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Integer reOrderThreshold;
    private Integer quantityInStock;
    private Long warehouseId;
}
