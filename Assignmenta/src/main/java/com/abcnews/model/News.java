package com.abcnews.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class News {
    private String id;
    private String title;
    private String content;
    private String image;
    private LocalDateTime postedDate;
    private String author;
    private int viewCount;
    private String categoryId;
    private boolean home;
    private boolean approved;

    public News() {}

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public LocalDateTime getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public boolean isHome() { return home; }
    public void setHome(boolean home) { this.home = home; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    // ✅ Thêm getter phụ cho JSTL
    public java.util.Date getPostedDateAsUtilDate() {
        if (this.postedDate == null) {
            return null;
        }
        // Chuyển đổi LocalDateTime sang java.sql.Timestamp (hoạt động với JSTL)
        return Timestamp.valueOf(this.postedDate);
    }
}

