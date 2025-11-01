package org.kashcode.inventorymanagementsystem.dtos.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class  PurchaseOrderRequest {
    private Long productId;
    private Long supplierId;
    private Long warehouseId;
    private Integer quantityOrdered;
    private LocalDate expectedArrivalDate;
}
