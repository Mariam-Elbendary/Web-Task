package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String SECRET = "my_super_secret_key_123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginInput = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        try {
            String role = ProductDb.getUserRole(loginInput, password);

            if (role != null) {
                String sessionId = UUID.randomUUID().toString();
                try (Jedis jedis = new Jedis("localhost", 6379)) {
                    jedis.hset("session:" + sessionId, "username", loginInput);
                    jedis.hset("session:" + sessionId, "role", role);
                    jedis.expire("session:" + sessionId, 1800);
                }


                Cookie sessionCookie = new Cookie("session_id", sessionId);
                response.addCookie(sessionCookie);
                response.sendRedirect("ProductsMain");

            } else {

                response.sendRedirect("login.jsp?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }