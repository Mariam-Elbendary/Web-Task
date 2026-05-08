package com.radi.demo7;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String role = (String) request.getAttribute("role");
        String idParam = request.getParameter("id");

        if (!"ADMIN".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "عفواً، لا يملك إلا الأدمن صلاحية حذف المنتجات.");
            return;
        }

        if (idParam != null) {
            try {
                int productId = Integer.parseInt(idParam);

                ProductDb.deleteProduct(productId);

                response.sendRedirect("ProductsMain");

            } catch (Exception e) {
                response.getWriter().println("Error: " + e.getMessage());
            }
        }
    }
}