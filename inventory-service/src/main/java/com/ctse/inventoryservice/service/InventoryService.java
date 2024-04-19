package com.ctse.inventoryservice.service;

import com.ctse.inventoryservice.dto.InventoryRequest;
import com.ctse.inventoryservice.model.Inventory;
import com.ctse.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public Inventory createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = mapper.map(inventoryRequest, Inventory.class);
        Integer quantity = inventory.getQuantity();
        inventory.setAvailable(quantity > 0);
        return inventoryRepository.save(inventory);
    }

    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCodeAndIsAvailable(skuCode, true).isPresent();
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }
}
