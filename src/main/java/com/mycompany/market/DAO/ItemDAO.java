/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.DAO;

import com.mycompany.market.model.Item;
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
public class ItemDAO {
public List<Item> getItemsByName(String keyword) {
    List<Item> result = new ArrayList<>();
    String sql = "SELECT * FROM tblItem WHERE name LIKE ?";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + keyword + "%");
        ResultSet rs = stmt.executeQuery();

       while (rs.next()) {
    result.add(new Item(
        rs.getInt("id"), 
        rs.getString("name"),
        rs.getDouble("sellingPrice"), 
        rs.getString("unit"),
        rs.getInt("stockQuantity"),   
        rs.getString("image"),
        rs.getString("description")
    ));
}

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
}

    public Item getItemById(int id) {
    String sql = "SELECT * FROM tblItem WHERE id = ?";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
    return new Item(
    rs.getInt("id"),
    rs.getString("name"),
    rs.getDouble("sellingPrice"),
    rs.getString("unit"),
    rs.getInt("stockQuantity"),
    rs.getString("image"),
    rs.getString("description")
);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}

