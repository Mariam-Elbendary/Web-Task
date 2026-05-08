package com.radi.demo7;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/addFeedback")
public class AddFeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = request.getParameter("feedbackContent");
        String username = (String) request.getSession().getAttribute("user");

        try {
            ProductDb.addFeedback(content, username);
            response.sendRedirect("ProductsMain");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ProductsMain");
        }
    }
}