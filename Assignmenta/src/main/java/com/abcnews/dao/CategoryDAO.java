package com.abcnews.dao;

import com.abcnews.model.Category;
import com.abcnews.utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> findAll() throws Exception {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT Id, Name FROM Categories ORDER BY Name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getString("Id"));
                c.setName(rs.getString("Name"));
                list.add(c);
            }
        }
        return list;
    }

    public Category findById(String id) throws Exception {
        String sql = "SELECT Id, Name FROM Categories WHERE Id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getString("Id"));
                    c.setName(rs.getString("Name"));
                    return c;
                }
            }
        }
        return null;
    }

    public void insert(Category c) throws Exception {
        String sql = "INSERT INTO Categories(Id, Name) VALUES(?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getName());
            ps.executeUpdate();
        }
    }

    public void update(Category c) throws Exception {
        String sql = "UPDATE Categories SET Name=? WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM Categories WHERE Id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}

