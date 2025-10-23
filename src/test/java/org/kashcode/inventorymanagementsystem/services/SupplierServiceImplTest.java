package org.kashcode.inventorymanagementsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.SupplierRequest;
import org.kashcode.inventorymanagementsystem.exceptions.SupplierNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        supplier = new Supplier();
        supplier.setSupplierId(1L);
        supplier.setName("Global Supplies Ltd");
        supplier.setContactInformation("globalsupplies@example.com");
    }

    @Test
    void testCreateSupplierSuccess() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Global Supplies Ltd");
        request.setContactInformation("globalsupplies@example.com");

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        var response = supplierService.createSupplier(request);

        assertNotNull(response);
        assertEquals("Global Supplies Ltd", response.getName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testGetAllSuppliersSuccess() {
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        var responses = supplierService.getAllSuppliers();

        assertEquals(1, responses.size());
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void testGetSupplierByIdFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        var response = supplierService.getSupplierById(1L);

        assertEquals("Global Supplies Ltd", response.getName());
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSupplierByIdNotFoundThrowsException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> supplierService.getSupplierById(1L));
        verify(supplierRepository, times(1)).findById(1L);
    }



    @Test
    void testUpdateSupplierSuccess() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Updated Supplier Ltd");
        request.setContactInformation("updated@example.com");

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        supplierService.updateSupplier(1L, request);

        assertEquals("Updated Supplier Ltd", supplier.getName());
        assertEquals("updated@example.com", supplier.getContactInformation());
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void testUpdateSupplierNotFoundThrowsException() {
        SupplierRequest request = new SupplierRequest();
        request.setName("Updated Supplier Ltd");
        request.setContactInformation("updated@example.com");

        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> supplierService.updateSupplier(1L, request));
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, never()).save(any());
    }



    @Test
    void testDeleteSupplierSuccess() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        supplierService.deleteSupplier(1L);

        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).delete(supplier);
    }

    @Test
    void testDeleteSupplierNotFoundThrowsException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> supplierService.deleteSupplier(1L));
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, never()).delete(any());
    }
}
