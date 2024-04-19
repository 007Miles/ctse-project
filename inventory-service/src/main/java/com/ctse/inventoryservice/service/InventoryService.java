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

    public Inventory updateInventoryById(Long id, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory == null) return null;
        if (inventoryRequest.getSkuCode() != null && !inventoryRequest.getSkuCode().isEmpty()) {
            inventory.setSkuCode(inventoryRequest.getSkuCode());
        }
        if (inventoryRequest.getQuantity() != null) {
            inventory.setQuantity(inventoryRequest.getQuantity());
            inventory.setAvailable(inventory.getQuantity() > 0);
        }
        return inventoryRepository.save(inventory);
    }

    public String  deleteInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if (inventory == null) return "No inventory exist with the given id: " + id;
        inventoryRepository.deleteById(id);
        return "Successfully deleted inventory with id: " + id;
    }
}
