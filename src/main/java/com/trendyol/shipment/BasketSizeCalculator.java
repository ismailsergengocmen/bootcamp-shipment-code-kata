package com.trendyol.shipment;

import java.util.*;

public class BasketSizeCalculator {
    final int BASKET_SHIPMENT_THRESHOLD = 3;
    Map<Integer, Integer> productSizeMap = new HashMap<>();

    public ShipmentSize calculateShipmentSize(List<Product> products) {
        fillProductSizeMap(products);

        int mostCommonItemSize = findMostCommonItemSize();
        int heaviestItemSize = findHeaviestItemSize();

        int commonItemCount = productSizeMap.get(mostCommonItemSize);

        int shipmentSize = (commonItemCount >= BASKET_SHIPMENT_THRESHOLD && mostCommonItemSize != 3) ? mostCommonItemSize + 1 : heaviestItemSize;

        return convertSizeToShipmentSizeEnum(shipmentSize);
    }

    private void fillProductSizeMap(List<Product> products){
        products.forEach(this::addItemToSizeMap);
    }

    private void addItemToSizeMap(Product product) {
        int priorityOfItemSize = convertShipmentSizeEnumToSize(product);
        productSizeMap.compute(priorityOfItemSize, (key, value) -> (value == null) ? 1 : value + 1);
    }

    private int convertShipmentSizeEnumToSize(Product product){
        return product.getSize().ordinal();
    }

    private ShipmentSize convertSizeToShipmentSizeEnum(int ordinalValue){
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
