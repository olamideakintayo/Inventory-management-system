package org.kashcode.inventorymanagementsystem.controllers;

import jakarta.validation.Valid;
import org.kashcode.inventorymanagementsystem.dtos.requests.PurchaseOrderRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.PurchaseOrderResponse;
import org.kashcode.inventorymanagementsystem.services.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(@Valid @RequestBody PurchaseOrderRequest request) {
        PurchaseOrderResponse savedOrder = purchaseOrderService.createOrder(request);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAllOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllOrders());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        purchaseOrderService.deleteOrder(orderId);
        return ResponseEntity.ok("Purchase order deleted successfully");
    }
}
