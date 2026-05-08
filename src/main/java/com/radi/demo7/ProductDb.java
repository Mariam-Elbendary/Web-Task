package com.radi.demo7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDb {
    private static final String URL = "jdbc:mysql://localhost:3307/products?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123#";
    private static final Gson gson = new Gson();

    public static List<Product> getProductList(String username) throws ClassNotFoundException, SQLException {
        try (Jedis jedis = new Jedis("localhost", 6379)) {

            String rateKey = "rate:" + username;
            if (jedis.exists(rateKey) && Integer.parseInt(jedis.get(rateKey)) >= 10) {
                throw new RuntimeException("Rate limit exceeded. Try again later.");
            }
            jedis.incr(rateKey);
            jedis.expire(rateKey, 60);
            String cached = jedis.get("products_cache");
            if (cached != null) {
                Type type = new TypeToken<ArrayList<Product>>() {}.getType();
                return gson.fromJson(cached, type);
            }

            ArrayList<Product> products = new ArrayList<>();
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM product_cards")) {

                while (rs.next()) {
                    products.add(new Product(rs.getInt("id"), rs.getFloat("price"), rs.getString("item")));
                }
            }

            jedis.setex("products_cache", 300, gson.toJson(products));
            return products;
        }
    }

    public static String getUserRole(String loginInput, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "SELECT role FROM users WHERE (username=? OR email=?) AND password=?";
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, loginInput);
            ps.setString(2, loginInput);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("role");
        }
        return null;
    }

    public static String registerUser(String username, String password, String email) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS)) {

            String checkQuery = "SELECT id FROM users WHERE email = ? OR username = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(checkQuery)) {
                checkPs.setString(1, email);
                checkPs.setString(2, username);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next()) {
                    return "exists";
                }
            }


            String insertQuery = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, 'USER')";
            try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {
                insertPs.setString(1, username);
                insertPs.setString(2, password);
                insertPs.setString(3, email);
                insertPs.executeUpdate();
                return "success";
            }
        }
    }




    public static void addProduct(Product p) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO product_cards (item, price) VALUES (?, ?)")) {
            ps.setString(1, p.getName());
            ps.setFloat(2, p.getPrice());
            ps.executeUpdate();

            try (Jedis jedis = new Jedis("localhost", 6379)) { jedis.del("products_cache"); }
        }
    }

    public static void deleteProduct(int id) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM product_cards WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();

            try (Jedis jedis = new Jedis("localhost", 6379)) { jedis.del("products_cache"); }
        }
    }

    public static void addFeedback(String content, String username) throws SQLException {
        String query = "INSERT INTO reviews (content, user_name) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/products?useSSL=false", "root", "123#");
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, content);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    public static List<Review> getFeedbacks() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT content, user_name FROM reviews";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/products?useSSL=false", "root", "123#");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reviews.add(new Review(rs.getString("content"), rs.getString("user_name")));
            }
        }
        return reviews;
    }
}