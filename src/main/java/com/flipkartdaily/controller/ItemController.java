package com.flipkartdaily.controller;

import com.flipkartdaily.dto.AddInventoryDto;
import com.flipkartdaily.dto.AddItemDto;
import com.flipkartdaily.dto.SearchRequestDto;
import com.flipkartdaily.entity.Item;
import com.flipkartdaily.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getItems());
    }
    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody AddItemDto item){

        itemService.addItem(item);
        return ResponseEntity.ok("Item added successfully");
    }

    @PostMapping("/inventory")
    public ResponseEntity<String> addInventory(@RequestBody
    AddInventoryDto addInventoryDto){
        itemService.addInventory(addInventoryDto);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    @PostMapping("/searchItem")
    public ResponseEntity<List<Item>> searchItems(@RequestBody SearchRequestDto searchRequestDto){
        List<Item> items = itemService.searchItems(searchRequestDto);
        return ResponseEntity.ok(items);

    }
}
