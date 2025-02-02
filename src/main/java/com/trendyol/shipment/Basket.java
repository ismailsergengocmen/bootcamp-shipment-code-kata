package com.trendyol.shipment;

import java.util.List;

public class Basket {

    private List<Product> products;
    private final BasketSizeCalculator basketSizeCalculator = new BasketSizeCalculator();
    public ShipmentSize getShipmentSize() {
        if (products.isEmpty()){
            throw new IllegalArgumentException("The basket is empty");
        }
        return basketSizeCalculator.calculateShipmentSize(this.getProducts());
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
