package com.abcnews.dao;

import com.abcnews.model.Newsletter;
import com.abcnews.utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsletterDAO {

    public void subscribe(String email) throws Exception {
        String sql = "MERGE INTO Newsletters AS T USING (SELECT ? AS Email) AS S ON T.Email = S.Email " +
                "WHEN MATCHED THEN UPDATE SET Enabled = 1 WHEN NOT MATCHED THEN INSERT (Email, Enabled) VALUES (?,1);";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    public List<String> getAllEmails() throws Exception {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Email FROM Newsletters WHERE Enabled = 1";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("Email"));
        }
        return list;
    }

    public List<Newsletter> findAll() throws Exception {
        List<Newsletter> list = new ArrayList<>();
        String sql = "SELECT Email, Enabled FROM Newsletters";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Newsletter n = new Newsletter();
                n.setEmail(rs.getString("Email"));
                n.setEnabled(rs.getBoolean("Enabled"));
                list.add(n);
            }
        }
        return list;
    }

    public void delete(String email) throws Exception {
        String sql = "DELETE FROM Newsletters WHERE Email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
    
    public void toggleEnabled(String email) throws Exception {
        // SQL: Đặt Enabled = 1 - Enabled (lật giá trị hiện tại)
        String sql = "UPDATE Newsletters SET Enabled = 1 - Enabled WHERE Email = ?"; 
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        }
    }
}

