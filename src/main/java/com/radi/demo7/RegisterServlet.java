package com.radi.demo7;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");

        try {
            String result = ProductDb.registerUser(user, pass, email);

            if ("exists".equals(result)) {
                request.setAttribute("errorMessage", "Username or Email already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                response.sendRedirect("login.jsp?msg=success");
            }
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}