package com.flipkartdaily.enums;

public enum SortCriteria {
    LOWEST_PRICE,
    HIGHEST_PRICE,
    LOWEST_QUANTITY, HIGHEST_QUANTITY;

    public static SortCriteria fromString(String sortBy) {
        if (sortBy == null) return LOWEST_PRICE; // default sort
        return switch (sortBy.toLowerCase()) {
            case "highest_price" -> HIGHEST_PRICE;
            case "lowest_quantity" -> LOWEST_QUANTITY;
            case "highest_quantity" -> HIGHEST_QUANTITY;
            default -> LOWEST_PRICE;
        };
    }
}
