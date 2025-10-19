package org.kashcode.inventorymanagementsystem.utils;

import org.kashcode.inventorymanagementsystem.data.models.PurchaseOrder;
import org.kashcode.inventorymanagementsystem.dtos.responses.PurchaseOrderResponse;

public class PurchaseOrderMapper {
    public static PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder order) {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setOrderId(order.getPurchaseOrderId());
        response.setProductName(order.getProduct().getName());
        response.setSupplierName(order.getSupplier().getName());
        response.setWarehouseName(order.getWarehouse().getName());
        response.setQuantityOrdered(order.getQuantityOrdered());
        response.setOrderDate(order.getOrderDate());
        response.setExpectedArrivalDate(order.getExpectedArrivalDate());
        return response;
    }
}
