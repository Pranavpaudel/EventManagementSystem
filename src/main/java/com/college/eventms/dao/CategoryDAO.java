package com.college.eventms.dao;

import com.college.eventms.entity.Category;
import com.college.eventms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the categories table — provides CRUD operations for event categories.
 */
public class CategoryDAO {

    /** Returns all categories in the database. */
    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    /** Returns the category with the given ID, or null if not found. */
    public Category getCategoryById(int categoryId) {

        String sql = "SELECT * FROM categories WHERE category_id = ?";
        Category category = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                category = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    /** Inserts a new category with the given name and description; returns true on success. */
    public boolean insertCategory(String name, String description) {

        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Updates an existing category's name and description; returns true on success. */
    public boolean updateCategory(Category category) {

        String sql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getCategoryId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Permanently deletes a category by ID; returns true on success. */
    public boolean deleteCategory(int categoryId) {

        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    }
}
