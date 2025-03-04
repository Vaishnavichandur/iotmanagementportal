package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manage")
public class ManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBConnection.getConnection()) {
            if ("delete".equals(action)) {
                String query = "DELETE FROM devices WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                ps.executeUpdate();
                response.sendRedirect("manage.html?status=deleted");
            } else if ("update".equals(action)) {
                String deviceName = request.getParameter("deviceName");
                String brand = request.getParameter("brand");
                String deviceType = request.getParameter("deviceType");
                String description = request.getParameter("description");

                String query = "UPDATE devices SET deviceName = ?, brand = ?, deviceType = ?, description = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, deviceName);
                ps.setString(2, brand);
                ps.setString(3, deviceType);
                ps.setString(4, description);
                ps.setInt(5, id);
                ps.executeUpdate();
                response.sendRedirect("manage.html?status=updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("manage.html?status=error");
        }
    }
}
