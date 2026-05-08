package com.radi.demo7;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {


    private static final String SECRET = "my_super_secret_key_123";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI().substring(request.getContextPath().length());


        try (Jedis jedis = new Jedis("localhost", 6379,2000)) {
            String ip = request.getRemoteAddr();
            String rateKey = "rate:" + ip;
            String currentRequests = jedis.get(rateKey);

            if (currentRequests != null && Integer.parseInt(currentRequests) > 20) {
                response.setStatus(429); // Too Many Requests
                response.getWriter().write("Too many requests. Please wait a minute.");
                return;
            }

            if (path.equals("/") || path.contains("register") || path.contains("login") || path.endsWith(".css")) {
                chain.doFilter(request, response);
                return;
            }
            jedis.incr(rateKey);
            if (currentRequests == null) jedis.expire(rateKey, 60);
        }
        if (path.contains("login") || path.contains("register") || path.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }
        if (path.contains("login") || path.contains("register") || path.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        String user = null;
        String role = "USER";
        String token = request.getParameter("token");
        if (token != null) {
            try {
                DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
                user = jwt.getClaim("user").asString();
                role = jwt.getClaim("role").asString();
            } catch (Exception e) {
                user = null;
            }
        }

        if (user == null) {
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                user = (String) session.getAttribute("user");
                role = (String) session.getAttribute("role");
            }
        }

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }


        if (path.startsWith("/admin") && !"ADMIN".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Admins Only!");
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("role", role);
        chain.doFilter(request, response);
    }
}