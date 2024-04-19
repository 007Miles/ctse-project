package com.ctse.inventoryservice.controller;

import com.ctse.inventoryservice.dto.InventoryRequest;
import com.ctse.inventoryservice.model.Inventory;
import com.ctse.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.createInventory(inventoryRequest);
    }

    @GetMapping("/available/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory updateInventoryById(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.updateInventoryById(id, inventoryRequest);
    }
}
