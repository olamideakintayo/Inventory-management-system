package org.kashcode.inventorymanagementsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WarehouseServiceImplTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        warehouse = new Warehouse();
        warehouse.setWarehouseId(1L);
        warehouse.setName("Main Warehouse");
        warehouse.setAddress("Lagos, Nigeria");
        warehouse.setCapacity(100);
    }

    @Test
    void testCreateWarehouseSuccess() {
        WarehouseRequest request = new WarehouseRequest();

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        var response = warehouseService.createWarehouse(request);

        assertNotNull(response);
        assertEquals("Main Warehouse", response.getName());
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

    @Test
    void testGetAllWarehousesSuccess() {
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));

        var responses = warehouseService.getAllWarehouses();

        assertEquals(1, responses.size());
        verify(warehouseRepository, times(1)).findAll();
    }

    @Test
    void testGetWarehouseByIdFound() {
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));

        var response = warehouseService.getWarehouseById(1L);

        assertEquals("Main Warehouse", response.getName());
        verify(warehouseRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWarehouseByIdNotFoundThrowsException() {
        when(warehouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class, () -> warehouseService.getWarehouseById(1L));
        verify(warehouseRepository, times(1)).findById(1L);
    }
}
