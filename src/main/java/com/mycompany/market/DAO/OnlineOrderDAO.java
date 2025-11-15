/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.DAO;

import com.mycompany.market.model.Customer;
import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.model.Item;
import com.mycompany.market.model.OnlineOrder;
import com.mycompany.market.model.OrderDetail;
import com.mycompany.market.model.WareHouseStaff;
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
public class OnlineOrderDAO {
   public List<OnlineOrder> getUnexportedOrders() {
        List<OnlineOrder> list = new ArrayList<>();

        String sql =
            "SELECT o.id, o.orderDate, " +
            "SUM(od.price * od.quantity * (1 - od.sale / 100)) AS totalAmount, " +
            "oo.deliveryAddress, oo.status, oo.processDate, " +
            "oo.DeliveryStafftblStaffId, m.Id AS CustomerTblMemberId, m.fullname AS customerName " +
            "FROM tblOrder o " +
            "JOIN tblOnlineOrder oo ON o.id = oo.tblOrderId " +
            "JOIN tblOrderDetail od ON o.id = od.tblOrderId " +
            "JOIN tblMember m ON oo.CustomerTblMemberId = m.Id " +
            "WHERE oo.status = 'Pending' " +
            "GROUP BY o.id, o.orderDate, oo.deliveryAddress, oo.status, " +
            "oo.processDate, oo.DeliveryStafftblStaffId, m.Id, m.fullname";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OnlineOrder order = new OnlineOrder();
                order.setId(rs.getInt("id"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setDeliveryAddress(rs.getString("deliveryAddress"));
                order.setStatus(rs.getString("status"));
                order.setProcessDate(rs.getDate("processDate"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("CustomerTblMemberId"));
                customer.setFullname(rs.getString("customerName")); 
                order.setCustomer(customer);

                list.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
  public OnlineOrder getOrderById(int id) {
    OnlineOrder order = null;

    String sql =
        "SELECT o.id, o.orderDate, " +
        "SUM(od.price * od.quantity * (1 - od.sale / 100)) AS totalAmount, " +
        "oo.deliveryAddress, oo.status, oo.processDate, " +
        "oo.DeliveryStafftblStaffId AS delivery_staff_id, " +
        "oo.WarehouseStafftblStaffId AS warehouse_staff_id, " +
        "m.Id AS CustomerTblMemberId, " +
        "m.fullname AS customer_name, m.phonenumber AS customer_phone " +
        "FROM tblOrder o " +
        "JOIN tblOnlineOrder oo ON o.id = oo.tblOrderId " +
        "JOIN tblMember m ON oo.CustomerTblMemberId = m.Id " +
        "JOIN tblOrderDetail od ON o.id = od.tblOrderId " +
        "WHERE o.id = ? " +
        "GROUP BY o.id, o.orderDate, oo.deliveryAddress, oo.status, " +
        "oo.processDate, oo.DeliveryStafftblStaffId, oo.WarehouseStafftblStaffId, " +
        "m.Id, m.fullname, m.phonenumber";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                order = new OnlineOrder();
                order.setId(rs.getInt("id"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setDeliveryAddress(rs.getString("deliveryAddress"));
                order.setStatus(rs.getString("status"));
                order.setProcessDate(rs.getDate("processDate"));

                // --- Customer ---
                Customer customer = new Customer();
                customer.setId(rs.getInt("CustomerTblMemberId"));
                customer.setFullname(rs.getString("customer_name"));
                customer.setPhonenumber(rs.getString("customer_phone"));
                order.setCustomer(customer);

                // --- Delivery Staff ---
                int staffId = rs.getInt("delivery_staff_id");
                if (staffId != 0) {
                    DeliveryStaff staff = new DeliveryStaff();
                    staff.setId(staffId);
                    order.setDeliverStaff(staff);
                }

                // --- Warehouse Staff ---
                int warehouseStaffId = rs.getInt("warehouse_staff_id");
                if (warehouseStaffId != 0) {
                    WareHouseStaff whStaff = new WareHouseStaff();
                    whStaff.setId(warehouseStaffId);
                    order.setWareHouseStaff(whStaff);
                }

                // --- Order Details ---
                String detailSql =
                    "SELECT od.tblItemId, i.name AS item_name, i.stockQuantity AS stock, " +
                    "od.quantity, od.price, " +
                    "(od.quantity * od.price * (1 - od.sale / 100)) AS subtotal " +
                    "FROM tblOrderDetail od " +
                    "JOIN tblItem i ON od.tblItemId = i.id " +
                    "WHERE od.tblOrderId = ?";

                try (PreparedStatement psDetail = conn.prepareStatement(detailSql)) {
                    psDetail.setInt(1, id);
                    try (ResultSet rsDetail = psDetail.executeQuery()) {
                        List<OrderDetail> orderDetails = new ArrayList<>();
                        while (rsDetail.next()) {
                            Item item = new Item();
                            item.setId(rsDetail.getInt("tblItemId"));
                            item.setName(rsDetail.getString("item_name"));
                            item.setStockQuantity(rsDetail.getInt("stock")); 

                            OrderDetail detail = new OrderDetail();
                            detail.setItem(item);
                            detail.setQuantity(rsDetail.getInt("quantity"));
                            detail.setPrice(rsDetail.getDouble("price"));
                            detail.setSubtotal(rsDetail.getDouble("subtotal"));
                            orderDetails.add(detail);
                        }
                        order.setOrderDetail(orderDetails);
                    }
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return order;
}
public boolean editOrder(OnlineOrder order) {
    String updateOrderSQL = "UPDATE tblOnlineOrder "
            + "SET DeliveryStafftblStaffId = ?, WarehouseStafftblStaffId = ?, status = ?, processDate = ? "
            + "WHERE tblOrderId = ?";

    String getOldStatusSQL = "SELECT status FROM tblOnlineOrder WHERE tblOrderId = ?";
    String getOrderDetailSQL = "SELECT tblItemId, quantity FROM tblOrderDetail WHERE tblOrderId = ?";
    String updateStockSQL = "UPDATE tblItem SET stockQuantity = stockQuantity - ? WHERE id = ?";

    try (Connection conn = DBUtil.getConnection()) {

        conn.setAutoCommit(false);  // IMPORTANT: start transaction

        String oldStatus = null;


        try (PreparedStatement ps = conn.prepareStatement(getOldStatusSQL)) {
            ps.setInt(1, order.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) oldStatus = rs.getString("status");
        }

        
        try (PreparedStatement ps = conn.prepareStatement(updateOrderSQL)) {

            if (order.getDeliverStaff() != null)
                ps.setInt(1, order.getDeliverStaff().getId());
            else
                ps.setNull(1, java.sql.Types.INTEGER);

            if (order.getWareHouseStaff() != null)
                ps.setInt(2, order.getWareHouseStaff().getId());
            else
                ps.setNull(2, java.sql.Types.INTEGER);

            ps.setString(3, order.getStatus());

            java.util.Date processDate = order.getProcessDate();
            if (processDate == null) processDate = new java.util.Date();
            ps.setDate(4, new java.sql.Date(processDate.getTime()));

            ps.setInt(5, order.getId());

            ps.executeUpdate();
        }

      
        boolean shouldUpdateStock =
                !"Processed".equalsIgnoreCase(oldStatus) &&
                !"Delivered".equalsIgnoreCase(oldStatus) &&
                ( "Processed".equalsIgnoreCase(order.getStatus()) ||
                  "Delivered".equalsIgnoreCase(order.getStatus()) );

        if (shouldUpdateStock) {
            try (PreparedStatement psDetail = conn.prepareStatement(getOrderDetailSQL);
                 PreparedStatement psStock = conn.prepareStatement(updateStockSQL)) {

                psDetail.setInt(1, order.getId());
                ResultSet rs = psDetail.executeQuery();

                while (rs.next()) {
                    int itemId = rs.getInt("tblItemId");
                    double qty = rs.getDouble("quantity");

                    psStock.setDouble(1, qty);
                    psStock.setInt(2, itemId);
                    psStock.executeUpdate();
                }
            }
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}




