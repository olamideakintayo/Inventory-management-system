package org.kashcode.inventorymanagementsystem.utils;

import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.dtos.requests.SupplierRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.SupplierResponse;

public class SupplierMapper {
    public Supplier toSupplierEntity(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setContactInformation(request.getContactInformation());
        return supplier;
    }

    public SupplierResponse toSupplierResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setSupplierId(supplier.getSupplierId());
        response.setName(supplier.getName());
        response.setContactInformation(supplier.getContactInformation());
        return response;
    }
}

