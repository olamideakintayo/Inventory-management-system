package org.kashcode.inventorymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;
import org.kashcode.inventorymanagementsystem.services.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseRequest request) {
        WarehouseResponse savedWarehouse = warehouseService.createWarehouse(request);
        return new ResponseEntity<>(savedWarehouse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Long warehouseId) {
        WarehouseResponse response = warehouseService.getWarehouseById(warehouseId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @PathVariable Long warehouseId,
            @Valid @RequestBody WarehouseRequest request) {
        WarehouseResponse response = warehouseService.updateWarehouse(warehouseId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.ok("Warehouse deleted successfully");
    }
}
