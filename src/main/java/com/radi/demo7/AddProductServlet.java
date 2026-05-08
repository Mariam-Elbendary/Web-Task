package com.radi.demo7;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = (String) request.getAttribute("role");

        if (!"ADMIN".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not allowed to add products");
            return;
        }

        String name = request.getParameter("name");
        float price = Float.parseFloat(request.getParameter("price"));

        try {
            ProductDb.addProduct(new Product(0, price, name));
            response.sendRedirect(request.getContextPath() + "/ProductsMain");
        } catch (Exception e) {
            response.getWriter().println("Error adding product: " + e.getMessage());
        }
    }
}