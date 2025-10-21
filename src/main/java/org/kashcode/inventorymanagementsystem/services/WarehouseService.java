package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;

import java.util.List;

public interface WarehouseService {
    WarehouseResponse createWarehouse(WarehouseRequest request);
    List<WarehouseResponse> getAllWarehouses();
    WarehouseResponse getWarehouseById(Long warehouseId);
}
