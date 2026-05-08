
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>E-Commerce Store</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: teal; margin: 40px; }
        .container { max-width: 900px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: darkslategray; text-align: center; }
        .user-info { text-align: right; margin-bottom: 20px; color: #666; font-size: 0.9em; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: center; }
        th { background-color: teal; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .btn-delete { color: #dc3545; text-decoration: none; font-weight: bold; }
        .btn-delete:hover { text-decoration: underline; }
        .admin-section { margin-top: 30px; padding: 15px; border: 1px dashed #007bff; border-radius: 5px; background: darkseagreen; }
        .logout-btn { color: teal; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <div class="user-info">
        Logged in as: <strong>${username}</strong> (${role}) |
        <a href="logout" class="logout-btn">Logout</a>
    </div>

    <h1>Product Collection</h1>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Product Name</th>
            <th>Price</th>
            <c:if test="${role == 'ADMIN'}">
                <th>Actions</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${data}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>$${item.price}</td>

                <c:if test="${role == 'ADMIN'}">
                    <td>
                        <a href="deleteProduct?id=${item.id}" class="btn-delete"
                           onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <c:if test="${role == 'ADMIN'}">
        <div class="admin-section">
            <h3 style="color: whitesmoke">Admin Panel: Add New Product</h3>
            <form action="addProduct" method="post">
                <input type="text" name="name" placeholder="Product Name" required
                       style="padding: 10px; border-radius:10px">
                <input type="number" step="0.01" name="price" placeholder="Price" required
                style="padding: 10px; border-radius:10px">
                <button type="submit" style="background:teal; color:white; border:none; padding:8px 15px; cursor:pointer; border-radius:4px;">
                    Add Product
                </button>
            </form>
        </div>
    </c:if>

    <div style="margin-top: 30px; border-top: 2px solid #007bff; padding-top: 20px;">
        <h3>Reviews & Feedback</h3>


        <c:if test="${role == 'USER'}">
            <form action="${pageContext.request.contextPath}/addFeedback" method="post" style="margin-bottom: 20px;">
                <input type="text" name="feedbackContent" placeholder="Write your feedback here..." required
                       style="width: 80%; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                <button type="submit" style="padding: 10px 20px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">
                    Post Feedback
                </button>
            </form>
        </c:if>


        <div id="feedback-list">
            <c:forEach var="rev" items="${reviews}">
                <div style="background: #f8f9fa; padding: 10px; margin-bottom: 10px; border-radius: 5px; border-left: 5px solid teal;">
                    <p style="margin: 0; font-weight: bold; color: #333;">${rev.user_name}:</p>
                    <p style="margin: 5px 0 0 0; color: #555;">${rev.content}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>