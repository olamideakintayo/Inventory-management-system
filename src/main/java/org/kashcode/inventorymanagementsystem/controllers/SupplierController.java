package org.kashcode.inventorymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.kashcode.inventorymanagementsystem.dtos.requests.SupplierRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.SupplierResponse;
import org.kashcode.inventorymanagementsystem.services.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest request) {
        SupplierResponse savedSupplier = supplierService.createSupplier(request);
        return new ResponseEntity<>(savedSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long supplierId) {
        SupplierResponse response = supplierService.getSupplierById(supplierId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<SupplierResponse> updateSupplier(
            @PathVariable Long supplierId,
            @Valid @RequestBody SupplierRequest request) {
        SupplierResponse response = supplierService.updateSupplier(supplierId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok("Supplier deleted successfully");
    }


}
