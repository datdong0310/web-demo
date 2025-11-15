/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.model;

/**
 *
 * @author DONG DAT
 */
public class OrderDetail {
    private int orderId;
    private Item item;
    private double quantity;
    private double price;
    private double subtotal;
    private double sale;
    
     public OrderDetail() {}

    public OrderDetail(int orderId, Item item, double quantity, double price, double subtotal, double sale) {
        this.orderId = orderId;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.sale = sale;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    
}
