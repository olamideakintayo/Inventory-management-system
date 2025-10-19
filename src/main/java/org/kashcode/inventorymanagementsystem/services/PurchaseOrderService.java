package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.dtos.requests.PurchaseOrderRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderResponse createOrder(PurchaseOrderRequest request);

    List<PurchaseOrderResponse> getAllOrders();

    void validateWarehouseCapacity(Warehouse warehouse, int quantityToAdd);


}
