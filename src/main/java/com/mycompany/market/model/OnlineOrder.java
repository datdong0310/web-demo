/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.model;

import java.sql.Date;

/**
 *
 * @author DONG DAT
 */
public class OnlineOrder extends Order{
    private Customer customer;
    private String deliveryAddress;
    private String status;
    private Date processDate;
    private DeliveryStaff deliverStaff;
    private WareHouseStaff warehouseStaff;
    
     public OnlineOrder() {
        super();
    }

    public OnlineOrder(int id, Date orderDate, Customer customer, String deliveryAddress, 
                       String status, Date processDate, DeliveryStaff deliverStaff, WareHouseStaff warehouseStaff) {
        super();
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.processDate = processDate;
        this.deliverStaff = deliverStaff;
        this.warehouseStaff = warehouseStaff;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public DeliveryStaff getDeliverStaff() {
        return deliverStaff;
    }

    public void setDeliverStaff(DeliveryStaff deliverStaff) {
        this.deliverStaff = deliverStaff;
    }
    
    public WareHouseStaff getWareHouseStaff() {
        return warehouseStaff;
    }

    public void setWareHouseStaff(WareHouseStaff warehouseStaff) {
        this.warehouseStaff = warehouseStaff;
    }
    

    
    
}
