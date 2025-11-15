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
public class Staff extends Member{
    private String role;
    public Staff(){
        super();
    };
      public Staff(int id, String username, String password, Date dateOfBirth, String gender, 
                    String phonenumber, String email, String address,
                    String role, String fullname) {
        super(id, username, password, dateOfBirth, gender, phonenumber, email, address, fullname);
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }  
    
}
