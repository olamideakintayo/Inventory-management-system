package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;
import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.data.models.PurchaseOrder;
import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.ProductRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.PurchaseOrderRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.exceptions.SupplierNotFoundException;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.kashcode.inventorymanagementsystem.utils.ProductMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              PurchaseOrderRepository purchaseOrderRepository,
                              SupplierRepository supplierRepository,
                              WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }

    @Override
    public ProductResponse createProduct(Product product) {
        return ProductMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void checkAndReorder(Product product) {
        if (product.getQuantityInStock() < product.getReOrderThreshold()) {
            Supplier supplier = supplierRepository.findAll().stream().findFirst().orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));

            Warehouse warehouse = warehouseRepository.findAll().stream().findFirst().orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
            if (supplier != null && warehouse != null) {
                PurchaseOrder order = new PurchaseOrder();
                order.setProduct(product);
                order.setSupplier(supplier);
                order.setWarehouse(warehouse);
                order.setQuantityOrdered(product.getReOrderThreshold() * 2);
                order.setOrderDate(LocalDate.now());
                order.setExpectedArrivalDate(LocalDate.now().plusDays(7));

                purchaseOrderRepository.save(order);
            }
        }
    }

    @Override
    public void updateStock(Long productId, int newQuantity) {
        productRepository.findById(productId).ifPresent(product -> {
            product.setQuantityInStock(newQuantity);
            productRepository.save(product);
            checkAndReorder(product);
        });
    }
}
