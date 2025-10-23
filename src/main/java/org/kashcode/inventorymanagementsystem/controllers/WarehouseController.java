package org.kashcode.inventorymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;
import org.kashcode.inventorymanagementsystem.services.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseRequest warehouse) {
        WarehouseResponse savedWarehouse = warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(savedWarehouse,HttpStatus.CREATED);

    }
}
