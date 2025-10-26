package com.abcnews.dao;

import com.abcnews.model.News;
import com.abcnews.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
    public List<News> getTopNews(int limit) throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT TOP (?) Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home FROM News ORDER BY ViewCount DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                News n = map(rs);
                list.add(n);
            }
        }
        return list;
    }
    public List<News> findAll() throws Exception {
        List<News> list = new ArrayList<>();
        String sql = "SELECT Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home FROM News ORDER BY PostedDate DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }
    public News findById(String id) throws Exception {
        String sql = "SELECT Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home FROM News WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }
    public void insert(News n) throws Exception {
        String sql = "INSERT INTO News(Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getId());
            ps.setString(2, n.getTitle());
            ps.setString(3, n.getContent());
            ps.setString(4, n.getImage());
            if (n.getPostedDate()!=null) ps.setDate(5, new java.sql.Date(n.getPostedDate().getTime())); else ps.setNull(5, Types.DATE);
            ps.setString(6, n.getAuthor());
            ps.setInt(7, n.getViewCount());
            ps.setString(8, n.getCategoryId());
            ps.setBoolean(9, n.isHome());
            ps.executeUpdate();
        }
    }
    public void update(News n) throws Exception {
        String sql = "UPDATE News SET Title=?, Content=?, Image=?, PostedDate=?, Author=?, ViewCount=?, CategoryId=?, Home=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, n.getTitle());
            ps.setString(2, n.getContent());
            ps.setString(3, n.getImage());
            if (n.getPostedDate()!=null) ps.setDate(4, new java.sql.Date(n.getPostedDate().getTime())); else ps.setNull(4, Types.DATE);
            ps.setString(5, n.getAuthor());
            ps.setInt(6, n.getViewCount());
            ps.setString(7, n.getCategoryId());
            ps.setBoolean(8, n.isHome());
            ps.setString(9, n.getId());
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
    private News map(ResultSet rs) throws SQLException {
        News n = new News();
        n.setId(rs.getString("Id"));
        n.setTitle(rs.getString("Title"));
        n.setContent(rs.getString("Content"));
        n.setImage(rs.getString("Image"));
        n.setPostedDate(rs.getDate("PostedDate"));
        n.setAuthor(rs.getString("Author"));
        n.setViewCount(rs.getInt("ViewCount"));
        n.setCategoryId(rs.getString("CategoryId"));
        n.setHome(rs.getBoolean("Home"));
        return n;
    }

public void setStatus(String id, String status) throws Exception {
    String sql = "UPDATE News SET Status = ? WHERE Id = ?";
    try (java.sql.Connection conn = com.abcnews.utils.DBContext.getConnection();
         java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setString(2, id);
        ps.executeUpdate();
    }
}

}