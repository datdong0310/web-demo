/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.DAO;

import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.model.Member;
import com.mycompany.market.model.WareHouseStaff;
import com.mycompany.market.util.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DONG DAT
 */
public class UserDAO {
   public Member login(String username, String password) {
        Member user = null;

        String sql = "SELECT * FROM tblMember WHERE username = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int memberId = rs.getInt("Id");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");
                String phone = rs.getString("phonenumber");
                String address = rs.getString("address");
                Date birth = rs.getDate("birthdate");
                String gender = rs.getString("gender");

                // ✅ Check if this member is also a staff
                String roleQuery = "SELECT role FROM tblStaff WHERE tblStaffId = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(roleQuery)) {
                    ps2.setInt(1, memberId);
                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        String role = rs2.getString("role");

                        // ✅ Instantiate subclass based on role
                        switch (role.toLowerCase()) {
                            case "warehouse":
                                user = new WareHouseStaff();
                                break;
                            case "delivery":
                                user = new DeliveryStaff();
                                break;
                
                        }
                    } else {
                       
                        user = new Member();
                    }
                }

                user.setId(memberId);
                user.setUsername(username);
                user.setFullname(fullname);
                user.setEmail(email);
                user.setPhonenumber(phone);
                user.setAddress(address);
                user.setDateOfBirth(birth);
                user.setGender(gender);
                user.setPassword(password); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; 
    }
}
