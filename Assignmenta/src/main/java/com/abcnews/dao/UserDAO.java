package com.abcnews.dao;

import com.abcnews.model.User;
import com.abcnews.utils.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                u.setBirthday(rs.getDate("Birthday"));
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                list.add(u);
            }
        }
        return list;
    }
    public void insert(User u) throws Exception {
        String sql = "INSERT INTO Users(Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getId());
            ps.setString(2, u.getPassword());
            if (u.getBirthday()!=null) ps.setDate(3, new java.sql.Date(u.getBirthday().getTime())); else ps.setNull(3, Types.DATE);
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
            if (u.getBirthday()!=null) ps.setDate(2, new java.sql.Date(u.getBirthday().getTime())); else ps.setNull(2, Types.DATE);
            ps.setBoolean(3, u.isGender());
            ps.setString(4, u.getMobile());
            ps.setString(5, u.getEmail());
            ps.setBoolean(6, u.isRole());
            ps.setString(7, u.getId());
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
    public User findById(String id) throws Exception {
        String sql = "SELECT Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role FROM Users WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setBirthday(rs.getDate("Birthday"));
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                return u;
            }
        }
        return null;
    }
    public User login(String id, String pw) throws Exception {
        String sql = "SELECT Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role FROM Users WHERE Id=? AND Password=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pw);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setBirthday(rs.getDate("Birthday"));
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                return u;
            }
        }
        return null;
    }

public void requestReporter(String fullname, String email) throws Exception {
    String sql = "INSERT INTO ReporterRequests(Fullname, Email) VALUES(?, ?)";
    try (java.sql.Connection conn = com.abcnews.utils.DBContext.getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, fullname);
        ps.setString(2, email);
        ps.executeUpdate();
    }
}

public java.util.List<java.util.Map<String,Object>> getPendingReporterRequests() throws Exception {
    java.util.List<java.util.Map<String,Object>> list = new java.util.ArrayList<>();
    String sql = "SELECT Id, Fullname, Email, RequestedAt FROM ReporterRequests ORDER BY RequestedAt DESC";
    try (java.sql.Connection conn = com.abcnews.utils.DBContext.getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {
        while(rs.next()){
            java.util.Map<String,Object> m = new java.util.HashMap<>();
            m.put("id", rs.getString("Id"));
            m.put("fullname", rs.getString("Fullname"));
            m.put("email", rs.getString("Email"));
            m.put("requestedAt", rs.getTimestamp("RequestedAt"));
            list.add(m);
        }
    }
    return list;
}

public void approveReporterByEmail(String email) throws Exception {
    String sql = "UPDATE Users SET Role = 1 WHERE Email = ?";
    try (java.sql.Connection conn = com.abcnews.utils.DBContext.getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, email);
        ps.executeUpdate();
    }
    String sql2 = "DELETE FROM ReporterRequests WHERE Email = ?";
    try (java.sql.Connection conn = com.abcnews.utils.DBContext.getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql2)) {
        ps.setString(1, email);
        ps.executeUpdate();
    }
}

}