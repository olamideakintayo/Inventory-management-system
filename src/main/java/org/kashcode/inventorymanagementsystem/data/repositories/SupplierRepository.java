package org.kashcode.inventorymanagementsystem.data.repositories;

import org.kashcode.inventorymanagementsystem.data.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
