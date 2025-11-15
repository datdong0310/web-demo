/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.market.controller;

/**
 *
 * @author DONG DAT
 */

import com.mycompany.market.DAO.ItemDAO;
import com.mycompany.market.model.Item;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {

    private ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "search";

        switch (action) {
            case "detail":
                showItemDetail(request, response);
                break;
            default:
                searchItems(request, response);
                break;
        }
    }

    private void searchItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        List<Item> itemList = null;

        // Nếu có keyword thì tìm, ngược lại để null để không hiện toàn bộ danh sách
        if (keyword != null && !keyword.trim().isEmpty()) {
            itemList = itemDAO.getItemsByName(keyword.trim());
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("itemList", itemList);

        
        request.getRequestDispatcher("search_item.jsp").forward(request, response);
    }

    private void showItemDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String keyword = request.getParameter("keyword");

        // Kiểm tra ID hợp lệ
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid item ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Item ID must be a number");
            return;
        }

        // Lấy thông tin chi tiết sản phẩm
        Item item = itemDAO.getItemById(id);
        if (item == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
            return;
        }

        // Gửi dữ liệu qua JSP
        request.setAttribute("item", item);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("item_detail.jsp").forward(request, response);
    }
}




