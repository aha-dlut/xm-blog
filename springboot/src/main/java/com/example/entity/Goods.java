package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
@author TanCheng
@create 2024 -03 -19    
*/
@ApiModel(description = "商品")
public class Goods {
    /*ID*/
    @ApiModelProperty("id")
    private Integer id;

    /*商品名*/
    @ApiModelProperty("商品名")
    private String name;

    /*商品价格*/
    @ApiModelProperty("价格")
    private Double price;

    /*商品描述*/
    @ApiModelProperty("商品描述")
    private String descr;

    /*商品封面*/
    @ApiModelProperty("商品封面")
    private String cover;

    /*卖家ID*/
    @ApiModelProperty("卖家id")
    private Integer userId;

    /*联系方式*/
    @ApiModelProperty("联系方式")
    private String association;

    /** 发布日期 */
    @ApiModelProperty("发布日期")
    private String date;

    /*是否上架*/
    @ApiModelProperty("是否上架")
    private Boolean sell;

    /*商品类型*/
    @ApiModelProperty("商品类型")
    private String type;

    /*这两个属性是帮助前端使用的*/
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("用户")
    private User user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getSell() {
        return sell;
    }

    public void setSell(Boolean sell) {
        this.sell = sell;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
