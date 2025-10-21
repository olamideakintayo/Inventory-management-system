package org.kashcode.inventorymanagementsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PurchaseOrderServiceImplTest {

    private PurchaseOrderRepository purchaseOrderRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private WarehouseRepository warehouseRepository;
    private PurchaseOrderServiceImpl purchaseOrderService;

    private Product product;
    private Supplier supplier;
    private Warehouse warehouse;
    private PurchaseOrderRequest request;

    @BeforeEach
    void setUp() {
        purchaseOrderRepository = mock(PurchaseOrderRepository.class);
        productRepository = mock(ProductRepository.class);
        supplierRepository = mock(SupplierRepository.class);
        warehouseRepository = mock(WarehouseRepository.class);

        purchaseOrderService = new PurchaseOrderServiceImpl(
                purchaseOrderRepository,
                productRepository,
                supplierRepository,
                warehouseRepository
        );

        // Mock entities
        product = new Product();
        product.setProductId(1L);
        product.setName("Test Product");
        product.setQuantityInStock(10);

        supplier = new Supplier();
        supplier.setSupplierId(1L);
        supplier.setName("Test Supplier");

        warehouse = new Warehouse();
        warehouse.setWarehouseId(1L);
        warehouse.setName("Main Warehouse");
        warehouse.setCapacity(100);
        warehouse.setProducts(List.of(product));

        request = new PurchaseOrderRequest();
        request.setProductId(1L);
        request.setSupplierId(1L);
        request.setWarehouseId(1L);
        request.setQuantityOrdered(20);
        request.setExpectedArrivalDate(LocalDate.now().plusDays(3));
    }

    @Test
    void createOrder_Successful() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderResponse response = purchaseOrderService.createOrder(request);

        assertNotNull(response);
        assertEquals("Test Product", response.getProductName());
        assertEquals("Test Supplier", response.getSupplierName());
        verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrder.class));
    }

    @Test
    void createOrder_ThrowsProductNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> purchaseOrderService.createOrder(request));
        verify(purchaseOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    void createOrder_ThrowsSupplierNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> purchaseOrderService.createOrder(request));
        verify(purchaseOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    void createOrder_ThrowsWarehouseNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class, () -> purchaseOrderService.createOrder(request));
        verify(purchaseOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    void createOrder_ThrowsWarehouseCapacityExceededException() {
        warehouse.setCapacity(20);
        warehouse.setProducts(List.of(product));
        product.setQuantityInStock(15);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        assertThrows(WarehouseCapacityExceededException.class, () -> purchaseOrderService.createOrder(request));
    }

    @Test
    void getAllOrders_ReturnsListOfResponses() {
        PurchaseOrder order = new PurchaseOrder();
        order.setProduct(product);
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.setQuantityOrdered(20);
        order.setOrderDate(LocalDate.now());
        order.setExpectedArrivalDate(LocalDate.now().plusDays(3));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(order));

        List<PurchaseOrderResponse> responses = purchaseOrderService.getAllOrders();

        assertEquals(1, responses.size());
        assertEquals("Test Product", responses.get(0).getProductName());
        verify(purchaseOrderRepository, times(1)).findAll();
    }
}
