package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/*
@author TanCheng
@create 2024 -03 -28    
*/
@ApiModel(description = "共享文件")
public class CommonFiles implements Serializable {

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 文件名称 */
    @ApiModelProperty("文件名")
    private String name;
    /** 是否文件夹 */
    @ApiModelProperty("是否是文件夹")
    private String folder;
    /** 文件路径 */
    @ApiModelProperty("文件路径")
    private String file;
    /** 文件类型 */
    @ApiModelProperty("文件类型，这个是jpg，zip的分类")
    private String type;
    /** 文件大小 */
    @ApiModelProperty("文件大小")
    private Double size;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private String createTime;
    /** 修改时间 */
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("父级目录id")
    private Integer folderId;
    @ApiModelProperty("根目录id")
    private Integer rootFolderId;

    /*文件分类*/
    @ApiModelProperty("文件分类，这个是前端存一个小列表，存分类")
    private String category;

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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public Integer getRootFolderId() {
        return rootFolderId;
    }

    public void setRootFolderId(Integer rootFolderId) {
        this.rootFolderId = rootFolderId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
