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
import org.kashcode.inventorymanagementsystem.dtos.requests.ProductRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.ProductResponse;
import org.kashcode.inventorymanagementsystem.exceptions.ProductNotFoundException;
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
        product.setDescription("Gaming laptop");
        product.setQuantityInStock(3);
        product.setReOrderThreshold(5);
    }

    @Test
    void getAllProducts_ShouldReturnMappedResponses_WhenProductsExist() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> responses = productService.getAllProducts();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("Laptop");
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
        var request = new ProductRequest();
        request.setName("Laptop");
        request.setDescription("High-end laptop");
        request.setQuantityInStock(3);
        request.setReOrderThreshold(5);
        request.setWarehouseId(1L);

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(1L);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(request);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenWarehouseNotFound() {
        var request = new ProductRequest();
        request.setWarehouseId(999L);

        when(warehouseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(request))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessage("Warehouse not found");
    }

    @Test
    void updateProduct_ShouldReturnUpdatedResponse_WhenProductExists() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setName("Old Laptop");
        existingProduct.setDescription("Old model");
        existingProduct.setQuantityInStock(10);
        existingProduct.setReOrderThreshold(5);

        ProductRequest request = new ProductRequest();
        request.setName("Updated Laptop");
        request.setDescription("New 2025 model");
        request.setQuantityInStock(20);
        request.setReOrderThreshold(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.updateProduct(productId, request);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Updated Laptop");
        assertThat(response.getDescription()).isEqualTo("New 2025 model");
        assertThat(response.getQuantityInStock()).isEqualTo(20);
        assertThat(response.getReOrderThreshold()).isEqualTo(10);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ProductRequest request = new ProductRequest();
        request.setName("nike shoe");
        request.setDescription("nike shoe");

        assertThatThrownBy(() -> productService.updateProduct(999L, request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found");

        verify(productRepository, never()).save(any());
    }

    @Test
    void checkAndReorder_ShouldCreatePurchaseOrder_WhenBelowThreshold() {
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
    void deleteProduct_ShouldDelete_WhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found");

        verify(productRepository, never()).delete(any());
    }
}
