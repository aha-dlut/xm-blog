package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色用户父类
 */
@ApiModel(description = "用户角色的基类")
public class Account {
    @ApiModelProperty("id")
    private Integer id;
    /** 用户名 */
    @ApiModelProperty("用户名")
    private String username;
    /** 名称 */
    @ApiModelProperty("名称")
    private String name;
    /** 密码 */
    @ApiModelProperty("密码")
    private String password;
    /** 角色标识 */
    @ApiModelProperty("角色标识")
    private String role;
    /** 新密码 */
    @ApiModelProperty("新密码")
    private String newPassword;
    /** 头像 */
    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("token作为登陆验证")
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
