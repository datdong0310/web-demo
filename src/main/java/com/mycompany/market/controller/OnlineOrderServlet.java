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
                 response.sendRedirect(request.getContextPath() + "/DeliverStaffServlet");
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
    
    // store in session so DeliverStaffServlet can access it
    HttpSession session = request.getSession();
    session.setAttribute("currentOrder", order);
    
    request.setAttribute("order", order);
    request.getRequestDispatcher("ProcessOrderView.jsp").forward(request, response);
}

    
   


 

    // 5️⃣ Save to DB
private void saveOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);
    if (session == null) {
        response.sendRedirect(request.getContextPath() + "/OnlineOrderServlet?action=list");
        return;
    }

    OnlineOrder order = (OnlineOrder) session.getAttribute("currentOrder");
    if (order == null) {
        response.sendRedirect(request.getContextPath() + "/OnlineOrderServlet?action=list");
        return;
    }

    try {
        // save to DB
        boolean saved = orderDAO.editOrder(order);
        if (!saved) throw new Exception("Failed to save order");

        request.setAttribute("order", order);
        request.setAttribute("action", "save");
        request.getRequestDispatcher("ConfirmView.jsp").forward(request, response);

        // remove from session after save
        session.removeAttribute("currentOrder");

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