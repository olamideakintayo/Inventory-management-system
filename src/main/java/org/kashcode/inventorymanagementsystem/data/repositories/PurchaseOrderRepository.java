package org.kashcode.inventorymanagementsystem.data.repositories;

import org.kashcode.inventorymanagementsystem.data.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
