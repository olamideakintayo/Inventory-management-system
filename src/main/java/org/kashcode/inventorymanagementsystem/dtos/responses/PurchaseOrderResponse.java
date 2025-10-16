package org.kashcode.inventorymanagementsystem.dtos.responses;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PurchaseOrderResponse {
    private Long orderId;
    private String productName;
    private String supplierName;
    private String warehouseName;
    private Integer quantityOrdered;
    private LocalDate orderDate;
    private LocalDate expectedArrivalDate;
}
