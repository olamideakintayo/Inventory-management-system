package org.kashcode.inventorymanagementsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.kashcode.inventorymanagementsystem.data.models.PurchaseOrder;
import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.ProductRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.PurchaseOrderRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;
import org.kashcode.inventorymanagementsystem.exceptions.SupplierNotFoundException;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1L);
        product.setName("Laptop");
        product.setQuantityInStock(3);
        product.setReOrderThreshold(5);
    }

    @Test
    void getAllProducts_ShouldReturnMappedResponses_WhenProductsExist() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> responses = productService.getAllProducts();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo(product.getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<ProductResponse> responses = productService.getAllProducts();

        assertThat(responses).isEmpty();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void createProduct_ShouldReturnMappedResponse_WhenProductIsSaved() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(product);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void checkAndReorder_ShouldCreatePurchaseOrder_WhenStockBelowThresholdAndDependenciesExist() {
        Supplier supplier = new Supplier();
        Warehouse warehouse = new Warehouse();

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));

        productService.checkAndReorder(product);

        verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrder.class));
    }

    @Test
    void checkAndReorder_ShouldThrowSupplierNotFoundException_WhenNoSupplierExists() {
        when(supplierRepository.findAll()).thenReturn(List.of());
        lenient().when(warehouseRepository.findAll()).thenReturn(List.of(new Warehouse()));

        assertThatThrownBy(() -> productService.checkAndReorder(product))
                .isInstanceOf(SupplierNotFoundException.class)
                .hasMessage("Supplier not found");

        verify(purchaseOrderRepository, never()).save(any());
    }

    @Test
    void checkAndReorder_ShouldThrowWarehouseNotFoundException_WhenNoWarehouseExists() {
        when(supplierRepository.findAll()).thenReturn(List.of(new Supplier()));
        when(warehouseRepository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() -> productService.checkAndReorder(product))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessage("Warehouse not found");

        verify(purchaseOrderRepository, never()).save(any());
    }

    @Test
    void checkAndReorder_ShouldNotCreateOrder_WhenStockAboveThreshold() {
        product.setQuantityInStock(10);
        productService.checkAndReorder(product);

        verify(purchaseOrderRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldUpdateProductAndTriggerReorder_WhenProductExists() {
        Supplier supplier = new Supplier();
        Warehouse warehouse = new Warehouse();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));

        productService.updateStock(1L, 2);

        verify(productRepository, times(1)).save(product);
        verify(purchaseOrderRepository, times(1)).save(argThat(order ->
                order.getProduct() == product &&
                        order.getSupplier() == supplier &&
                        order.getWarehouse() == warehouse &&
                        order.getQuantityOrdered() == product.getReOrderThreshold() * 2
        ));
    }

    @Test
    void updateStock_ShouldDoNothing_WhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.updateStock(1L, 2);

        verify(productRepository, never()).save(any());
        verify(purchaseOrderRepository, never()).save(any());
    }
}
