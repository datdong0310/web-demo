package com.mycompany.market.model;

public class Item {
    private int id;
    private String name;
    private double sellingPrice;
    private String unit;
    private int stockQuantity;
    private String image;
    private String description;

    public Item() {}

    public Item(int id, String name, double sellingPrice,
                String unit, int stockQuantity, String image, String description) {
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
      
        this.unit = unit;
        this.stockQuantity = stockQuantity;
        this.image = image;
        this.description = description;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }


    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}