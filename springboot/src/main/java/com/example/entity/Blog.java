package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * 博客信息
 */
@ApiModel(description = "博客类")
public class Blog {

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 标题 */
    @ApiModelProperty("标题")
    private String title;
    /** 内容 */
    @ApiModelProperty("内容")
    private String content;
    /** 简介 */
    @ApiModelProperty("简介")
    private String descr;
    /** 封面 */
    @ApiModelProperty("封面")
    private String cover;
    /** 标签 */
//    private String tags;
    /** 发布人ID */
    @ApiModelProperty("发布者id")
    private Integer userId;
    /** 发布日期 */
    @ApiModelProperty("发布日期")
    private String date;
    /** 浏览量 */
    @ApiModelProperty("浏览量")
    private Integer readCount;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("分类名")
    private String categoryName;
    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户")
    private User user;

    @ApiModelProperty("点赞数")
    private Integer likesCount;
    @ApiModelProperty("是否被当前用户点赞")
    private Boolean userLike;  // 返回当前浏览的数据是否被当前登录的用户点赞
    @ApiModelProperty("收藏数")
    private Integer collectCount;
    @ApiModelProperty("是否被当前用户收藏")
    private Boolean userCollect;

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Boolean getUserCollect() {
        return userCollect;
    }

    public void setUserCollect(Boolean userCollect) {
        this.userCollect = userCollect;
    }

    public Boolean getUserLike() {
        return userLike;
    }

    public void setUserLike(Boolean userLike) {
        this.userLike = userLike;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

//    public String getTags() {
//        return tags;
//    }

//    public void setTags(String tags) {
//        this.tags = tags;
//    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blog)) return false;
        Blog blog = (Blog) o;
        return Objects.equals(getId(), blog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}