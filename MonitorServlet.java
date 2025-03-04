package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/monitor")
public class MonitorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM devices");

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Monitor Devices</title></head><body>");
            html.append("<h1>Monitor Devices</h1>");
            html.append("<table border='1'><tr><th>ID</th><th>Device Name</th><th>Brand</th><th>Type</th><th>Description</th></tr>");

            while (rs.next()) {
                html.append("<tr><td>").append(rs.getInt("id"))
                    .append("</td><td>").append(rs.getString("deviceName"))
                    .append("</td><td>").append(rs.getString("brand"))
                    .append("</td><td>").append(rs.getString("deviceType"))
                    .append("</td><td>").append(rs.getString("description"))
                    .append("</td></tr>");
            }

            html.append("</table>");
            html.append("</body></html>");
            response.getWriter().write(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error loading device data.");
        }
    }
}
