package com.flipkartdaily.service;

import com.flipkartdaily.dto.AddInventoryDto;
import com.flipkartdaily.dto.AddItemDto;
import com.flipkartdaily.dto.SearchRequestDto;
import com.flipkartdaily.entity.Item;

import java.util.List;

public interface ItemService {
    void addItem(AddItemDto addItemDto);
    void addInventory(AddInventoryDto addInventoryDto);
    List<Item> searchItems(SearchRequestDto request);
    List<Item> getItems();
}
