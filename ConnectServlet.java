package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/connect")
public class ConnectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deviceName = request.getParameter("deviceName");
        String brand = request.getParameter("brand");
        String deviceType = request.getParameter("deviceType");
        String description = request.getParameter("description");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO devices (deviceName, brand, deviceType, description) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, deviceName);
            ps.setString(2, brand);
            ps.setString(3, deviceType);
            ps.setString(4, description);
            ps.executeUpdate();
            response.sendRedirect("connect.html?status=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("connect.html?status=error");
        }
    }
}
