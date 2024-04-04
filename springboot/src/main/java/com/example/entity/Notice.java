package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 公告信息表
*/
@ApiModel(description = "公告通知")
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 标题 */
    @ApiModelProperty("标题")
    private String title;
    /** 内容 */
    @ApiModelProperty("内容")
    private String content;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private String time;
    /** 创建人 */
    @ApiModelProperty("创建人")
    private String user;

    /*封面*/
    @ApiModelProperty("封面")
    private String cover;

    /*分类id*/
    @ApiModelProperty("分类id")
    private Integer categoryId;

    /*分类名*/
    @ApiModelProperty("分类名")
    private String categoryName;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}