/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.controller;

import com.mycompany.market.DAO.UserDAO;
import com.mycompany.market.model.DeliveryStaff;
import com.mycompany.market.model.Member;
import com.mycompany.market.model.WareHouseStaff;
import java.io.IOException;
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
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Member user = userDAO.login(username, password);

        if (user != null) {
            
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            
            if (user instanceof WareHouseStaff) {
                response.sendRedirect("StaffMenuView.jsp");
            } else if (user instanceof DeliveryStaff) {
                response.sendRedirect("StaffMenuView.jsp");
            } else {
                response.sendRedirect("CustomerMenuView.jsp");
            }

        } else {
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}