package com.abcnews.dao;

import com.abcnews.model.News;
import com.abcnews.utils.DBContext;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class NewsDAO {

    private News mapRow(ResultSet rs) throws SQLException {
        News n = new News();
        n.setId(rs.getString("Id"));
        n.setTitle(rs.getString("Title"));
        n.setContent(rs.getString("Content"));
        n.setImage(rs.getString("Image"));

        Date postedDate = rs.getDate("PostedDate");
        if (postedDate != null) {
            n.setPostedDate(postedDate.toLocalDate().atStartOfDay());
        }

        n.setAuthor(rs.getString("Author"));
        n.setViewCount(rs.getInt("ViewCount"));
        n.setCategoryId(rs.getString("CategoryId"));
        n.setHome(rs.getBoolean("Home"));
        n.setApproved(rs.getBoolean("Approved"));
        return n;
    }

    // ------------------- CRUD -------------------

    public List<News> findAll() throws Exception {
        String sql = "SELECT * FROM News ORDER BY PostedDate DESC";
        List<News> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public News findById(String id) throws Exception {
        String sql = "SELECT * FROM News WHERE Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public void insert(News n) throws Exception {
        String sql = """
            INSERT INTO News (Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home, Approved)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getId());
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getContent());
            ps.setString(4, n.getImage());
            if (n.getPostedDate() != null)
                ps.setDate(5, java.sql.Date.valueOf(n.getPostedDate().toLocalDate()));
            else ps.setNull(5, Types.DATE);
            ps.setString(6, n.getAuthor());
            ps.setInt(7, n.getViewCount());
            ps.setString(8, n.getCategoryId());
            ps.setBoolean(9, n.isHome());
            ps.setBoolean(10, n.isApproved());
            ps.executeUpdate();
        }
    }

    public void update(News n) throws Exception {
        String sql = """
            UPDATE News SET Title=?, Content=?, Image=?, PostedDate=?, Author=?, 
                            ViewCount=?, CategoryId=?, Home=?, Approved=? WHERE Id=?
        """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getTitle());
            ps.setString(2, n.getContent());
            ps.setString(3, n.getImage());
            if (n.getPostedDate() != null)
                ps.setDate(4, java.sql.Date.valueOf(n.getPostedDate().toLocalDate()));
            else ps.setNull(4, Types.DATE);
            ps.setString(5, n.getAuthor());
            ps.setInt(6, n.getViewCount());
            ps.setString(7, n.getCategoryId());
            ps.setBoolean(8, n.isHome());
            ps.setBoolean(9, n.isApproved());
            ps.setString(10, n.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM News WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // ------------------- BUSINESS LOGIC -------------------

    /** Phê duyệt tin */
    public void approveNews(String id, boolean approved) throws Exception {
        String sql = "UPDATE News SET Approved=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, approved);
            ps.setString(2, id);
            ps.executeUpdate();
        }
    }

    /** Tăng lượt xem */
    public void increaseViewCount(String id) throws Exception {
        String sql = "UPDATE News SET ViewCount = ViewCount + 1 WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    /** Lấy danh sách bài chờ duyệt */
    public List<News> findPending() throws Exception {
        String sql = "SELECT * FROM News WHERE Approved = 0 ORDER BY PostedDate DESC";
        List<News> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    /** Lấy bài viết theo tác giả (phóng viên) */
    public List<Map<String, Object>> findByAuthor(String authorId) throws Exception {
        String sql = """
            SELECT Id, Title, ViewCount, PostedDate
            FROM News
            WHERE Author = ?
            ORDER BY PostedDate DESC
        """;
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", rs.getString("Id"));
                    map.put("title", rs.getString("Title"));
                    map.put("viewCount", rs.getInt("ViewCount"));
                    Date d = rs.getDate("PostedDate");
                    if (d != null) map.put("postedDateAsDate", d);
                    list.add(map);
                }
            }
        }
        return list;
    }

    /** Tin nổi bật theo lượt xem (KHÔNG CẦN DUYỆT) */
    public List<News> getTopNews(int limit) throws Exception {
        // ĐÃ SỬA: Loại bỏ "WHERE Approved = 1"
        String sql = "SELECT TOP (?) * FROM News ORDER BY ViewCount DESC";
        List<News> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Tin mới nhất (KHÔNG CẦN DUYỆT) */
    public List<News> findLatest(int limit) throws Exception {
        // ĐÃ SỬA: Loại bỏ "WHERE Approved = 1"
        String sql = "SELECT TOP (?) * FROM News ORDER BY PostedDate DESC";
        List<News> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }
    
    /** Tin Trang Chủ (KHÔNG CẦN DUYỆT) */
    public List<News> findHomeNews() throws Exception {
        // ĐÃ SỬA: Loại bỏ "AND Approved = 1"
        String sql = "SELECT * FROM News WHERE Home = 1 ORDER BY PostedDate DESC";
        List<News> list = new ArrayList<>();
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }
    
    /** Tin Liên Quan (KHÔNG CẦN DUYỆT) */
    public List<News> findRelatedNews(String categoryId, String currentNewsId, int limit) throws Exception {
        // ĐÃ SỬA: Loại bỏ "AND Approved = 1"
        String sql = "SELECT TOP (?) * FROM News "
                    + "WHERE CategoryId = ? AND Id <> ? "
                    + "ORDER BY PostedDate DESC";
        List<News> list = new ArrayList<>();
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            ps.setString(2, categoryId);
            ps.setString(3, currentNewsId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /**
     * Lấy tin nổi bật cho Sidebar
     */
    public List<News> findHotSidebarNews(int limit) throws Exception {
        // Sử dụng getTopNews (đã loại bỏ điều kiện duyệt)
        return getTopNews(limit);
    }
}
