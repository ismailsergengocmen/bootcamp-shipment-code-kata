package com.trendyol.shipment;

import java.util.*;

public class BasketSizeCalculator {
    final int BASKET_SHIPMENT_THRESHOLD = 3;
    Map<Integer, Integer> productSizeMap = new HashMap<>();

    public ShipmentSize calculateShipmentSize(List<Product> products) {
        fillProductSizeMap(products);
        int mostCommonItemSize = findMostCommonItemSize();
        int heaviestItemSize = findHeaviestItemSize();

        if (mostCommonItemSize == -1){
            return ShipmentSize.SMALL;
        }
        if (productSizeMap.get(mostCommonItemSize) >= BASKET_SHIPMENT_THRESHOLD){
            if (mostCommonItemSize != 3) {
                return convertPriorityToShipmentSize(mostCommonItemSize + 1);
            }
            else {
                return convertPriorityToShipmentSize(mostCommonItemSize);
            }
        }
        else {
            return convertPriorityToShipmentSize(heaviestItemSize);
        }
    }

    private void fillProductSizeMap(List<Product> products){
        products.forEach(product -> addItem(product));
    }

    // Method to add an item of a specific size
    private void addItem(Product product) {
        int priorityOfItemSize = convertShipmentSizeToPriority(product);
        if (productSizeMap.containsKey(priorityOfItemSize)) {
            int count = productSizeMap.get(priorityOfItemSize);
            productSizeMap.put(priorityOfItemSize, count + 1);
        }
        else {
            productSizeMap.put(priorityOfItemSize, 1);
        }
    }

    private int convertShipmentSizeToPriority(Product product){
        return product.getSize().ordinal();
    }

    private ShipmentSize convertPriorityToShipmentSize(int ordinalValue){
        return ShipmentSize.values()[ordinalValue];
    }

    private int findMostCommonItemSize(){
        return productSizeMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    private int findHeaviestItemSize(){
        return productSizeMap.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

}
