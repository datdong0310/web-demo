/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.market.controller;

import com.mycompany.market.DAO.DeliveryStaffDAO;
import com.mycompany.market.DAO.OnlineOrderDAO;
import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.model.Member;
import com.mycompany.market.model.OnlineOrder;
import com.mycompany.market.model.WareHouseStaff;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import javax.servlet.RequestDispatcher;
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
@WebServlet("/DeliverStaffServlet")
public class DeliverStaffServlet extends HttpServlet {

    private DeliveryStaffDAO staffDAO = new DeliveryStaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OnlineOrder order = (OnlineOrder) request.getAttribute("order");

       
        if (order == null && request.getParameter("orderId") != null) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            order = new OnlineOrderDAO().getOrderById(orderId);
        }

        List<DeliveryStaff> staffList = staffDAO.getAllDeliveryStaff();

        request.setAttribute("order", order);
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("AssignStaffView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int staffId = Integer.parseInt(request.getParameter("staffId"));
        int orderId = Integer.parseInt(request.getParameter("orderId"));

      
        OnlineOrderDAO orderDAO = new OnlineOrderDAO();
        DeliveryStaffDAO staffDAO = new DeliveryStaffDAO();

        OnlineOrder order = orderDAO.getOrderById(orderId);
        
        DeliveryStaff chosen = staffDAO.getDeliveryStaffById(staffId);
         HttpSession session = request.getSession(false);
    if (session != null) {
        Member user = (Member) session.getAttribute("user");
        if (user instanceof WareHouseStaff) {
            order.setWareHouseStaff((WareHouseStaff) user);
        }
    }

        order.setDeliverStaff(chosen);
        order.setProcessDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus("Processed");

      
        request.setAttribute("order", order);
        request.setAttribute("staff", chosen);
        request.getRequestDispatcher("ConfirmView.jsp").forward(request, response);
    }
}
