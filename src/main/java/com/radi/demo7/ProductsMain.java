package com.radi.demo7;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/ProductsMain")
public class ProductsMain extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = (String) request.getAttribute("user");
        String role = (String) request.getAttribute("role");

        try {
            List<Product> data = ProductDb.getProductList(user);

            List<Review> reviewList = ProductDb.getFeedbacks();

            request.setAttribute("data", data);
            request.setAttribute("role", role);
            request.setAttribute("username", user);
            request.setAttribute("reviews", reviewList);

            request.getRequestDispatcher("display-products.jsp").forward(request, response);

        } catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println("<h2 style='color:red;'>Error: " + e.getMessage() + "</h2>");
            response.getWriter().println("<a href='login.jsp'>Back to Login</a>");
        }
    }
}