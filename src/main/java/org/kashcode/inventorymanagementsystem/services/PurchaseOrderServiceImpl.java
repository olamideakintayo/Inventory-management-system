package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.data.models.PurchaseOrder;
import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.ProductRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.PurchaseOrderRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.PurchaseOrderRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.PurchaseOrderResponse;
import org.kashcode.inventorymanagementsystem.exceptions.ProductNotFoundException;
import org.kashcode.inventorymanagementsystem.exceptions.SupplierNotFoundException;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseCapacityExceededException;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.kashcode.inventorymanagementsystem.utils.PurchaseOrderMapper;

import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                ProductRepository productRepository,
                                SupplierRepository supplierRepository,
                                WarehouseRepository warehouseRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public PurchaseOrderResponse createOrder(PurchaseOrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        validateWarehouseCapacity(warehouse, request.getQuantityOrdered());

        PurchaseOrder order = new PurchaseOrder();
        order.setProduct(product);
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.setQuantityOrdered(request.getQuantityOrdered());
        order.setOrderDate(LocalDate.now());
        order.setExpectedArrivalDate(request.getExpectedArrivalDate());

        purchaseOrderRepository.save(order);

        return PurchaseOrderMapper.toPurchaseOrderResponse(order);
    }

    @Override
    public List<PurchaseOrderResponse> getAllOrders() {
        return purchaseOrderRepository.findAll().stream().map(PurchaseOrderMapper::toPurchaseOrderResponse).toList();
    }

    @Override
    public void validateWarehouseCapacity(Warehouse warehouse, int quantityToAdd) {
        int totalStock = warehouse.getProducts().stream().mapToInt(Product::getQuantityInStock).sum();
        if (totalStock + quantityToAdd > warehouse.getCapacity()) {
            throw new WarehouseCapacityExceededException("Warehouse capacity exceeded");
        }

    }

}
