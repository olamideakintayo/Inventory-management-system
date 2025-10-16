package org.kashcode.inventorymanagementsystem.dtos.responses;

import lombok.Data;

@Data
public class WarehouseResponse {
    private Long warehouseId;
    private String name;
    private String address;
    private Integer capacity;
}
