package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 收藏模块
 */
@ApiModel(description = "收藏")
public class Collect {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("我也不知道")
    private Integer fid;
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("我也没看懂")
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
