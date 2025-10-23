package org.kashcode.inventorymanagementsystem.services;

import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.kashcode.inventorymanagementsystem.data.repositories.SupplierRepository;
import org.kashcode.inventorymanagementsystem.dtos.requests.SupplierRequest;
import org.kashcode.inventorymanagementsystem.dtos.responses.SupplierResponse;
import org.kashcode.inventorymanagementsystem.exceptions.SupplierNotFoundException;
import org.kashcode.inventorymanagementsystem.utils.SupplierMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = SupplierMapper.toSupplierEntity(request);

        Supplier savedSupplier = supplierRepository.save(supplier);
        return SupplierMapper.toSupplierResponse(savedSupplier);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierMapper::toSupplierResponse)
                .toList();
    }

    @Override
    public SupplierResponse getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));
        return SupplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public void updateSupplier(Long supplierId, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));
        supplier.setName(request.getName());
        supplier.setContactInformation(request.getContactInformation());
        supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new SupplierNotFoundException("Supplier not found"));
        supplierRepository.delete(supplier);
    }


}
