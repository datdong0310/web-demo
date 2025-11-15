/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.model;

/**
 *
 * @author DONG DAT
 */
public class DeliveryStaff extends Staff{
    public DeliveryStaff(){
        super();
    };
    private int currentAssign;
     public int getCurrentAssign() {
        return currentAssign;
    }

    public void setCurrentAssign(int assign) {
        this.currentAssign = assign;
    }  
}
