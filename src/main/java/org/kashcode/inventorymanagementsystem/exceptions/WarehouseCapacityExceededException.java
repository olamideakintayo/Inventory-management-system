package org.kashcode.inventorymanagementsystem.exceptions;

public class WarehouseCapacityExceededException extends RuntimeException {
    public WarehouseCapacityExceededException(String message) {
        super(message);
    }
}
