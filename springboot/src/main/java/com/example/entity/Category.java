package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 博客分类
 */
@ApiModel(description = "分类")
public class Category {

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 分类名称 */
    @ApiModelProperty("名称")
    private String name;

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

}