package org.kashcode.inventorymanagementsystem.dtos.requests;

import lombok.Data;

@Data
public class WarehouseRequest {
    private String name;
    private String location;
    private Integer capacity;
}
