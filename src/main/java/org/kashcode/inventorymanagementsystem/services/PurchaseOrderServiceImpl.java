package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.ProductRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.PurchaseOrderRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.PurchaseOrderRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.PurchaseOrderResponse;

import java.util.List;

public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository orderRepository,
                                ProductRepository productRepository,
                                SupplierRepository supplierRepository,
                                WarehouseRepository warehouseRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public PurchaseOrderResponse createOrder(PurchaseOrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        validateWarehouseCapacity(warehouse, request.getQuantityOrdered());

        PurchaseOrder order = new PurchaseOrder();
        order.setProduct(product);
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.setQuantityOrdered(request.getQuantityOrdered());
        order.setOrderDate(LocalDate.now());
        order.setExpectedArrivalDate(request.getExpectedArrivalDate());

        orderRepository.save(order);

        return mapper.toPurchaseOrderResponse(order);
    }

    @Override
    public List<PurchaseOrderResponse> getAllOrders() {

    }

    @Override
    public void validateWarehouseCapacity(Warehouse warehouse, int quantityToAdd) {

    }

}
