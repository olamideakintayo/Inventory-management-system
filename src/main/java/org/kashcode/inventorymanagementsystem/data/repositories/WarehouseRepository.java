package org.kashcode.inventorymanagementsystem.data.repositories;

import org.kashcode.inventorymanagementsystem.data.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
