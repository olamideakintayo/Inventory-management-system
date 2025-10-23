package org.kashcode.inventorymanagementsystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WarehouseServiceImplTest {

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
        request.setName("Main Warehouse");
        request.setAddress("Lagos, Nigeria");
        request.setCapacity(100);

        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        WarehouseResponse response = warehouseService.createWarehouse(request);

        assertNotNull(response);
        assertEquals("Main Warehouse", response.getName());
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }

    @Test
    void testGetAllWarehousesSuccess() {
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));

        var responses = warehouseService.getAllWarehouses();

        assertEquals(1, responses.size());
        assertEquals("Main Warehouse", responses.get(0).getName());
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

    @Test
    void testUpdateWarehouseSuccess() {
        // Arrange
        WarehouseRequest request = new WarehouseRequest();
        request.setName("Updated Warehouse");
        request.setAddress("Abuja, Nigeria");
        request.setCapacity(200);

        Warehouse existingWarehouse = new Warehouse();
        existingWarehouse.setWarehouseId(1L);
        existingWarehouse.setName("Old Warehouse");
        existingWarehouse.setAddress("Lagos, Nigeria");
        existingWarehouse.setCapacity(100);

        Warehouse updatedWarehouse = new Warehouse();
        updatedWarehouse.setWarehouseId(1L);
        updatedWarehouse.setName("Updated Warehouse");
        updatedWarehouse.setAddress("Abuja, Nigeria");
        updatedWarehouse.setCapacity(200);

        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(existingWarehouse));
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(updatedWarehouse);


        var response = warehouseService.updateWarehouse(1L, request);


        assertNotNull(response);
        assertEquals(1L, response.getWarehouseId());
        assertEquals("Updated Warehouse", response.getName());
        assertEquals("Abuja, Nigeria", response.getAddress());
        assertEquals(200, response.getCapacity());

        verify(warehouseRepository, times(1)).findById(1L);
        verify(warehouseRepository, times(1)).save(any(Warehouse.class));
    }


    @Test
    void testUpdateWarehouseNotFoundThrowsException() {
        WarehouseRequest request = new WarehouseRequest();
        request.setName("Updated Warehouse");

        when(warehouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class, () -> warehouseService.updateWarehouse(1L, request));
        verify(warehouseRepository, times(1)).findById(1L);
        verify(warehouseRepository, never()).save(any(Warehouse.class));
    }

    @Test
    void testDeleteWarehouseSuccess() {
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
        doNothing().when(warehouseRepository).delete(warehouse);

        warehouseService.deleteWarehouse(1L);

        verify(warehouseRepository, times(1)).findById(1L);
        verify(warehouseRepository, times(1)).delete(warehouse);
    }

    @Test
    void testDeleteWarehouseNotFoundThrowsException() {
        when(warehouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class, () -> warehouseService.deleteWarehouse(1L));
        verify(warehouseRepository, times(1)).findById(1L);
        verify(warehouseRepository, never()).delete(any(Warehouse.class));
    }
}
