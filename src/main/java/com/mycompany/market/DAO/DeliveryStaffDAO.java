/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.DAO;

import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DONG DAT
 */
public class DeliveryStaffDAO {

    public List<DeliveryStaff> getAllDeliveryStaff() {
        List<DeliveryStaff> result = new ArrayList<>();

        String sql = 
            "SELECT ds.tblStaffId AS deliveryStaffId, " +
            "m.username, m.fullname, m.birthdate AS dateOfBirth, " +
            "m.gender, m.phonenumber, m.email, m.address, s.role, " +
            "COUNT(o.id) AS processed_orders " +
            "FROM deliverystaff ds " +
            "JOIN tblstaff s ON ds.tblStaffId = s.Id " +
            "JOIN tblmember m ON s.tblStaffId = m.Id " +
            "LEFT JOIN tblonlineorder o " +
            "ON ds.tblStaffId = o.DeliveryStafftblStaffId " +
            "AND o.status = 'processed' " +
            "GROUP BY ds.tblStaffId, m.username, m.fullname, m.birthdate, " +
            "m.gender, m.phonenumber, m.email, m.address, s.role " +
            "HAVING COUNT(o.id) < 40 " +
            "ORDER BY processed_orders ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DeliveryStaff staff = new DeliveryStaff();
                staff.setId(rs.getInt("deliveryStaffId"));
                staff.setUsername(rs.getString("username"));
                staff.setFullname(rs.getString("fullname"));
                staff.setDateOfBirth(rs.getDate("dateOfBirth"));
                staff.setGender(rs.getString("gender"));
                staff.setPhonenumber(rs.getString("phonenumber"));
                staff.setEmail(rs.getString("email"));
                staff.setAddress(rs.getString("address"));
                staff.setRole(rs.getString("role"));
                staff.setCurrentAssign(rs.getInt("processed_orders"));
                result.add(staff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public DeliveryStaff getDeliveryStaffById(int id) {
        String sql =
            "SELECT ds.tblStaffId AS deliveryStaffId, " +
            "m.username, m.fullname, m.birthdate AS dateOfBirth, " +
            "m.gender, m.phonenumber, m.email, m.address, s.role " +
            "FROM deliverystaff ds " +
            "JOIN tblstaff s ON ds.tblStaffId = s.Id " +
            "JOIN tblmember m ON s.tblStaffId = m.Id " +
            "WHERE ds.tblStaffId = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DeliveryStaff staff = new DeliveryStaff();
                staff.setId(rs.getInt("deliveryStaffId"));
                staff.setUsername(rs.getString("username"));
                staff.setFullname(rs.getString("fullname"));
                staff.setDateOfBirth(rs.getDate("dateOfBirth"));
                staff.setGender(rs.getString("gender"));
                staff.setPhonenumber(rs.getString("phonenumber"));
                staff.setEmail(rs.getString("email"));
                staff.setAddress(rs.getString("address"));
                staff.setRole(rs.getString("role"));
                return staff;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
