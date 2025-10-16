package org.kashcode.inventorymanagementsystem.dtos.responses;

import lombok.Data;

@Data
public class SupplierResponse {
    private Long supplierId;
    private String name;
    private String contactInformation;

}
