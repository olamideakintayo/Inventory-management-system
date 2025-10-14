package org.kashcode.inventorymanagementsystem.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        private String name;;

        @Column(nullable = false)
        private String contactInformation;
}

