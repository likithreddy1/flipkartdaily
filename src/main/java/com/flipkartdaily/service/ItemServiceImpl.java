package com.flipkartdaily.service;

import com.flipkartdaily.dto.AddInventoryDto;
import com.flipkartdaily.dto.AddItemDto;
import com.flipkartdaily.dto.SearchRequestDto;
import com.flipkartdaily.entity.Item;
import com.flipkartdaily.enums.SortCriteria;
import com.flipkartdaily.exception.DuplicateItemException;
import com.flipkartdaily.exception.InvalidInputException;
import com.flipkartdaily.exception.InventoryOperationException;
import com.flipkartdaily.exception.ItemNotFoundException;
import com.flipkartdaily.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    public ItemServiceImpl(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public void addItem(AddItemDto addItemDto){
        Optional<Item> itemExists = itemRepository.findByBrandAndCategoryAndPrice(addItemDto.getBrand(),addItemDto.getCategory(),addItemDto.getPrice());
        if(itemExists.isPresent()){
            throw new DuplicateItemException("Item with same brand, category, and price already exists.");
        }

        Item item = new Item();
        item.setBrand(addItemDto.getBrand());
        item.setCategory(addItemDto.getCategory());
        item.setPrice(addItemDto.getPrice());
        item.setQuantity(0);
        itemRepository.save(item);

    }

    //adding quantity to the items
    @Override
    public void addInventory(AddInventoryDto addInventoryDto){
        if(addInventoryDto.getQuantity()<=0){
            throw new InvalidInputException("quantity of the item should be greater than 0");
        }
        Item item = itemRepository.findByBrandAndCategoryAndPrice(addInventoryDto.getBrand(),addInventoryDto.getCategory(),addInventoryDto.getPrice())
                .orElseThrow(()-> new InventoryOperationException("Item not found to add the inventory"));

        item.setQuantity(item.getQuantity()+addInventoryDto.getQuantity());
        itemRepository.save(item);
    }

    @Override
    public List<Item> searchItems(SearchRequestDto request) {

        if (request.getPriceFrom() != null && request.getPriceTo() != null &&
                request.getPriceFrom() > request.getPriceTo()) {
            throw new InvalidInputException("Price range is invalid: priceFrom > priceTo");
        }

        List<Item> items = itemRepository.findAll();

        //filtering
        List<Item> filteredItems = new java.util.ArrayList<>(items.stream()
                .filter(item -> request.getBrands() == null || request.getBrands().contains(item.getBrand()))
                .filter(item -> request.getCategories() == null || request.getCategories().contains(item.getCategory()))
                .filter(item -> request.getPriceFrom() == null || item.getPrice() >= request.getPriceFrom())
                .filter(item -> request.getPriceTo() == null || item.getPrice() <= request.getPriceTo())
                .toList());

        if (filteredItems.isEmpty()) {
            throw new ItemNotFoundException("No items found for the applied filters.");
        }
        //sorting logic
        SortCriteria sortCriteria = SortCriteria.fromString(request.getSortBy());
        Comparator<Item> comparator = switch (sortCriteria){
            case HIGHEST_PRICE -> Comparator.comparingInt(Item::getPrice).reversed();
            case LOWEST_QUANTITY -> Comparator.comparingInt(Item::getQuantity);
            case HIGHEST_QUANTITY -> Comparator.comparingInt(Item::getQuantity).reversed();
            default -> Comparator.comparing(Item::getPrice);
        };
        filteredItems.sort(comparator);
        return filteredItems;

    }
}
