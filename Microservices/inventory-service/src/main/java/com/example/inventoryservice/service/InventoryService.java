package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional
    public List<InventoryResponse> isInstock(List<String> skucode){
        return inventoryRepository.findBySkucodeIn(skucode).stream().
                map(inventory ->
                      InventoryResponse.builder().skucode(inventory.getSkucode()).
                            isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();
    }
}
