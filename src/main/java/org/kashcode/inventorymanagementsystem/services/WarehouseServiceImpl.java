package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.kashcode.inventorymanagementsystem.data.repositories.WarehouseRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.WarehouseRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.WarehouseResponse;
import org.kashcode.inventorymanagementsystem.exceptions.WarehouseNotFoundException;
import org.kashcode.inventorymanagementsystem.utils.WarehouseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        Warehouse warehouse = WarehouseMapper.toWarehouseEntity(request);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return WarehouseMapper.toWarehouseResponse(savedWarehouse);
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseMapper::toWarehouseResponse)
                .toList();
    }

    @Override
    public WarehouseResponse getWarehouseById(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        return WarehouseMapper.toWarehouseResponse(warehouse);
    }

    @Override
    public WarehouseResponse updateWarehouse(Long warehouseId, WarehouseRequest request) {
        Warehouse existingWarehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));

        Warehouse updatedWarehouse = WarehouseMapper.toWarehouseEntity(request);
        updatedWarehouse.setWarehouseId(existingWarehouse.getWarehouseId());
        updatedWarehouse.setProducts(existingWarehouse.getProducts());

        Warehouse savedWarehouse = warehouseRepository.save(updatedWarehouse);
        return WarehouseMapper.toWarehouseResponse(savedWarehouse);
    }


    @Override
    public void deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        warehouseRepository.delete(warehouse);
    }


}
