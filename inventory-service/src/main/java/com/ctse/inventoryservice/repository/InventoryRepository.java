package com.ctse.inventoryservice.repository;

import com.ctse.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCodeAndIsAvailable(String skuCode, boolean isAvailable);
}
