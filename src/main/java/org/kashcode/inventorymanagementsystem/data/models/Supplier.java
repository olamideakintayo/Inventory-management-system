package com.souk.inventory.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contactInformation;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Product> suppliedProducts;
}
