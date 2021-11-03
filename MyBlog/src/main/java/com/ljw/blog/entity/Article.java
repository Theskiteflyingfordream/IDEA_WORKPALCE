package com.ljw.blog.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    private Integer articleId;

    private Integer articleUserId;

    private String articleTitle;

    private Integer articleViewCount;

    private Integer articleCommentCount;

    private Integer articleLikeCount;

    private Integer articleIsComment;

    private Integer articleStatus;

    private Integer articleOrder;

    private Date articleUpdateTime;

    private Date articleCreateTime;

    private String articleSummary;

    private String articleThumbnail;

    private String articleContent;

    /*非数据库字段*/
    private List<Comment> commentList;
    private List<Category> categoryList;
    private User user;
    private List<Tag> tagList;

    public Article(Integer articleId, Integer articleUserId, String articleTitle, Integer articleViewCount, Integer articleCommentCount, Integer articleLikeCount, Integer articleIsComment, Integer articleStatus, Integer articleOrder, Date articleUpdateTime, Date articleCreateTime, String articleSummary, String articleThumbnail, String articleContent, List<Comment> commentList, List<Category> categoryList, User user, List<Tag> tagList) {
        this.articleId = articleId;
        this.articleUserId = articleUserId;
        this.articleTitle = articleTitle;
        this.articleViewCount = articleViewCount;
        this.articleCommentCount = articleCommentCount;
        this.articleLikeCount = articleLikeCount;
        this.articleIsComment = articleIsComment;
        this.articleStatus = articleStatus;
        this.articleOrder = articleOrder;
        this.articleUpdateTime = articleUpdateTime;
        this.articleCreateTime = articleCreateTime;
        this.articleSummary = articleSummary;
        this.articleThumbnail = articleThumbnail;
        this.articleContent = articleContent;
        this.commentList = commentList;
        this.categoryList = categoryList;
        this.user = user;
        this.tagList = tagList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article() {
    }



    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getArticleUserId() {
        return articleUserId;
    }

    public void setArticleUserId(Integer articleUserId) {
        this.articleUserId = articleUserId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getArticleViewCount() {
        return articleViewCount;
    }

    public void setArticleViewCount(Integer articleViewCount) {
        this.articleViewCount = articleViewCount;
    }

    public Integer getArticleCommentCount() {
        return articleCommentCount;
    }

    public void setArticleCommentCount(Integer articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    public Integer getArticleLikeCount() {
        return articleLikeCount;
    }

    public void setArticleLikeCount(Integer articleLikeCount) {
        this.articleLikeCount = articleLikeCount;
    }

    public Integer getArticleIsComment() {
        return articleIsComment;
    }

    public void setArticleIsComment(Integer articleIsComment) {
        this.articleIsComment = articleIsComment;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public Integer getArticleOrder() {
        return articleOrder;
    }

    public void setArticleOrder(Integer articleOrder) {
        this.articleOrder = articleOrder;
    }

    public Date getArticleUpdateTime() {
        return articleUpdateTime;
    }

    public void setArticleUpdateTime(Date articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    public String getArticleThumbnail() {
        return articleThumbnail;
    }

    public void setArticleThumbnail(String articleThumbnail) {
        this.articleThumbnail = articleThumbnail;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}