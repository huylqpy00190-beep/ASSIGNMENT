package com.abcnews.dao;

import com.abcnews.model.User;
import com.abcnews.utils.DBContext;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    public List<User> findAll() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role FROM Users";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                Date d = rs.getDate("Birthday");
                if (d != null) u.setBirthday(d.toLocalDate());
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                list.add(u);
            }
        }
        return list;
    }

    public User findById(String id) throws Exception {
        String sql = "SELECT Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role FROM Users WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getString("Id"));
                    u.setPassword(rs.getString("Password"));
                    u.setFullname(rs.getString("Fullname"));
                    Date d = rs.getDate("Birthday");
                    if (d != null) u.setBirthday(d.toLocalDate());
                    u.setGender(rs.getBoolean("Gender"));
                    u.setMobile(rs.getString("Mobile"));
                    u.setEmail(rs.getString("Email"));
                    u.setRole(rs.getBoolean("Role"));
                    return u;
                }
            }
        }
        return null;
    }

    public void insert(User u) throws Exception {
        String sql = "INSERT INTO Users(Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getId());
            ps.setString(2, u.getPassword());
            if (u.getBirthday() != null) ps.setDate(3, Date.valueOf(u.getBirthday())); else ps.setNull(3, Types.DATE);
            ps.setBoolean(4, u.isGender());
            ps.setString(5, u.getMobile());
            ps.setString(6, u.getEmail());
            ps.setBoolean(7, u.isRole());
            ps.executeUpdate();
        }
    }

    public void update(User u) throws Exception {
        String sql = "UPDATE Users SET Password=?, Fullname=?, Birthday=?, Gender=?, Mobile=?, Email=?, Role=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getPassword());
            ps.setString(2, u.getFullname());
            if (u.getBirthday() != null) ps.setDate(3, Date.valueOf(u.getBirthday())); else ps.setNull(3, Types.DATE);
            ps.setBoolean(4, u.isGender());
            ps.setString(5, u.getMobile());
            ps.setString(6, u.getEmail());
            ps.setBoolean(7, u.isRole());
            ps.setString(8, u.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM Users WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public User login(String id, String pw) throws Exception {
        String sql = "SELECT Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role FROM Users WHERE Id=? AND Password=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pw);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getString("Id"));
                    u.setPassword(rs.getString("Password"));
                    u.setFullname(rs.getString("Fullname"));
                    Date d = rs.getDate("Birthday");
                    if (d != null) u.setBirthday(d.toLocalDate());
                    u.setGender(rs.getBoolean("Gender"));
                    u.setMobile(rs.getString("Mobile"));
                    u.setEmail(rs.getString("Email"));
                    u.setRole(rs.getBoolean("Role"));
                    return u;
                }
            }
        }
        return null;
    }

    // Reporter requests
    public void requestReporter(String fullname, String email) throws Exception {
        String sql = "INSERT INTO ReporterRequests(Fullname, Email) VALUES(?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    public List<Map<String,Object>> getPendingReporterRequests() throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "SELECT Id, Fullname, Email, RequestedAt FROM ReporterRequests ORDER BY RequestedAt DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("id", rs.getInt("Id"));
                m.put("fullname", rs.getString("Fullname"));
                m.put("email", rs.getString("Email"));
                m.put("requestedAt", rs.getTimestamp("RequestedAt"));
                list.add(m);
            }
        }
        return list;
    }

    public void approveReporterByEmail(String email) throws Exception {
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Nếu đã có user, set Role = 0 (phóng viên)
                String updateSql = "UPDATE Users SET Role = 0 WHERE Email = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, email);
                    ps.executeUpdate();
                }
                // Nếu chưa có user, tạo user mới với Id = NEWID(), password default '123456'
                String insertSql = "IF NOT EXISTS (SELECT 1 FROM Users WHERE Email = ?) " +
                        "INSERT INTO Users(Id, Password, Fullname, Email, Role) " +
                        "SELECT CONVERT(varchar(50), NEWID()), '123456', Fullname, Email, 0 FROM ReporterRequests WHERE Email = ?";
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setString(1, email);
                    ps.setString(2, email);
                    ps.executeUpdate();
                }
                String deleteSql = "DELETE FROM ReporterRequests WHERE Email = ?";
                try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                    ps.setString(1, email);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
