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

    // 3️⃣ Forward full order to DeliverStaffServlet
    private void forwardToAssignStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));
        OnlineOrder order = orderDAO.getOrderById(orderId); // get full order (includes customer etc.)

        request.setAttribute("order", order);
        request.getRequestDispatcher("DeliverStaffServlet").forward(request, response); // 👈 forward object directly
    }

    // 4️⃣ Confirm page

    // 5️⃣ Save to DB
private void saveOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    OnlineOrderDAO orderDAO = new OnlineOrderDAO();
    DeliveryStaffDAO staffDAO = new DeliveryStaffDAO(); // DAO to fetch full DeliveryStaff
    try {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        // 1️⃣ Fetch full order
        OnlineOrder order = orderDAO.getOrderById(orderId);
        if (order == null) throw new Exception("Order not found");

        // 2️⃣ Fetch full DeliveryStaff from DB
        DeliveryStaff deliverStaff = staffDAO.getDeliveryStaffById(staffId);
        if (deliverStaff == null) throw new Exception("Delivery staff not found");

        // 3️⃣ Set order fields
        order.setStatus("Processed");
        order.setDeliverStaff(deliverStaff);

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("user");
            if (user instanceof WareHouseStaff) {
                order.setWareHouseStaff((WareHouseStaff) user);
            }
        }

        // 4️⃣ Save to DB
        boolean saved = orderDAO.editOrder(order);
        if (!saved) throw new Exception("Failed to save order");

        // 5️⃣ Forward to JSP with full objects for printing
        request.setAttribute("order", order);
        request.setAttribute("action", "save"); // trigger print in JSP
        request.getRequestDispatcher("ConfirmView.jsp").forward(request, response);

    } catch (Exception e) {
        request.setAttribute("errorMessage", e.getMessage());
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