/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.market.controller;

import com.mycompany.market.DAO.DeliveryStaffDAO;
import com.mycompany.market.DAO.OnlineOrderDAO;
import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.model.OnlineOrder;
import com.mycompany.market.model.WareHouseStaff;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DONG DAT
 */
@WebServlet("/OnlineOrderServlet")
public class OnlineOrderServlet extends HttpServlet {

    private OnlineOrderDAO orderDAO = new OnlineOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                listOrders(request, response);
                break;
            case "detail":
                showOrderDetail(request, response);
                break;
            case "assignStaff": 
                forwardToAssignStaff(request, response);
                break;
            default:
                listOrders(request, response);
                break;
        }
    }

    // 1️⃣ Show list
    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<OnlineOrder> orders = orderDAO.getUnexportedOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("OrderListView.jsp").forward(request, response);
    }

    // 2️⃣ Show details
    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("id"));
        OnlineOrder order = orderDAO.getOrderById(orderId);
        request.setAttribute("order", order);
        request.getRequestDispatcher("ProcessOrderView.jsp").forward(request, response);
    }

    
    private void forwardToAssignStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));
        OnlineOrder order = orderDAO.getOrderById(orderId); // get full order (includes customer etc.)

        request.setAttribute("order", order);
        request.getRequestDispatcher("DeliverStaffServlet").forward(request, response); // 👈 forward object directly
    }

  
 private void saveOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String logFilePath = "F:/CNPM/order_debug.log"; // adjust path if needed

    try (PrintWriter log = new PrintWriter(new FileWriter(logFilePath, true))) {

        log.println("=== saveOrder started ===");
        log.println("Request parameters: orderId=" + request.getParameter("orderId")
                + ", staffId=" + request.getParameter("staffId"));

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        OnlineOrder order = orderDAO.getOrderById(orderId);
        if (order == null) {
            log.println("Order not found with ID: " + orderId);
            throw new Exception("Order not found with ID: " + orderId);
        }

        log.println("Order found: ID=" + order.getId());

        order.setStatus("Processed");
        log.println("Order status set to 'Processed'");

        DeliveryStaff staff = new DeliveryStaff();
        staff.setId(staffId);
        order.setDeliverStaff(staff);
        log.println("Delivery staff set: ID=" + staffId);

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("user");
            if (user instanceof WareHouseStaff) {
                order.setWareHouseStaff((WareHouseStaff) user);
                log.println("Warehouse staff set from session: " + ((WareHouseStaff) user).getFullname());
            } else {
                log.println("No warehouse staff in session");
            }
        }

        boolean saved = orderDAO.editOrder(order);
        log.println("DAO editOrder returned: " + saved);

        if (!saved) {
            log.println("Failed to save order to database!");
            throw new Exception("Failed to save order in database!");
        }

        log.println("Order saved successfully!");
        log.println("=== saveOrder finished ===\n");

       
        request.setAttribute("order", order);
        request.getRequestDispatcher("ConfirmView.jsp").forward(request, response);

    } catch (Exception e) {
        // Log exception
        try (PrintWriter log = new PrintWriter(new FileWriter(logFilePath, true))) {
            log.println("=== Exception in saveOrder ===");
            e.printStackTrace(log);
            log.println("==============================\n");
        }

       
        request.setAttribute("order", request.getAttribute("order")); // keep order if available
        request.setAttribute("errorMessage", e.getMessage());
        request.setAttribute("exceptionStackTrace", e);
        request.getRequestDispatcher("ConfirmView.jsp").forward(request, response);
    }
}



    
    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");
    if ("save".equals(action)) {
        saveOrder(request, response);
    }
}

}