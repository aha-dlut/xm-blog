package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户")
public class User extends Account {

    @ApiModelProperty("id")
    private Integer id;//id
    @ApiModelProperty("用户名")
    private String username;//用户名（学号）
    @ApiModelProperty("密码")
    private String password;//密码
    @ApiModelProperty("姓名")
    private String name;//姓名
    @ApiModelProperty("头像")
    private String avatar;//头像
    @ApiModelProperty("角色")
    private String role;//角色
    @ApiModelProperty("性别")
    private String sex;//性别
    @ApiModelProperty("电话")
    private String phone;//电话
    @ApiModelProperty("邮箱")
    private String email;///邮箱
    @ApiModelProperty("个人信息")
    private String info;
    @ApiModelProperty("生日")
    private String birth;//生日
    @ApiModelProperty("博客数")
    private Integer blogCount;
    @ApiModelProperty("点赞数")
    private Integer likesCount;
    @ApiModelProperty("收藏数")
    private Integer collectCount;

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
