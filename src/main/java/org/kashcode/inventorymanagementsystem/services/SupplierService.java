package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.dtos.requests.SupplierRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.SupplierResponse;

import java.util.List;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    List<SupplierResponse> getAllSuppliers();
    SupplierResponse getSupplierById(Long supplierId);

    void deleteSupplier(Long supplierId);

    void updateSupplier(Long supplierId, SupplierRequest request);
}
