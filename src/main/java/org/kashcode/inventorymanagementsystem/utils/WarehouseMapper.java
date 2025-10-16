package org.kashcode.inventorymanagementsystem.utils;

import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;

public class WarehouseMapper {
    public Warehouse toWarehouseEntity(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        warehouse.setCapacity(request.getCapacity());
        return warehouse;
    }

    public WarehouseResponse toWarehouseResponse(Warehouse warehouse) {
        WarehouseResponse response = new WarehouseResponse();
        response.setWarehouseId(warehouse.getWarehouseId());
        response.setName(warehouse.getName());
        response.setAddress(warehouse.getAddress());
        response.setCapacity(warehouse.getCapacity());
        return response;
    }

}
