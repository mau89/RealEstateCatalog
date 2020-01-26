package com.example.realestatecatalog.data;

public class PropertyAdd {
    public String getItemTextAddress() {
        return itemTextAddress;
    }

    public String getItemTextArea() {
        return itemTextArea;
    }

    public String getItemImageProperty() {
        return itemImageProperty;
    }

    public String getItemTextPrice() {
        return itemTextPrice;
    }

    public int getCurrentID() {
        return currentID;
    }

    private String itemTextAddress;
    private String itemTextArea;
    private String itemImageProperty;
    private String itemTextPrice;
    private int currentID;

    public PropertyAdd(String itemTextAddress, String itemTextArea, String itemImageProperty, String itemTextPrice, int currentID) {
        this.itemTextAddress = itemTextAddress;
        this.itemTextArea = itemTextArea;
        this.itemImageProperty = itemImageProperty;
        this.itemTextPrice = itemTextPrice;
        this.currentID = currentID;
    }
}
