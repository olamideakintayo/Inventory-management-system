package org.kashcode.inventorymanagementsystem.data.repositories;

import org.kashcode.inventorymanagementsystem.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
