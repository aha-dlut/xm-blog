package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 点赞模块
 */
@ApiModel(description = "点赞")
public class Likes {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("点赞的博客的id")
    private Integer fid;
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("不知道是什么")
    private String module;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
