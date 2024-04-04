package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 网盘文件
 */
@ApiModel(description = "网盘文件")
public class DiskFiles implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 文件名称 */
    @ApiModelProperty("文件名")
    private String name;
    /** 是否文件夹 */
    @ApiModelProperty("是否文件夹")
    private String folder;
    /** 文件路径 */
    @ApiModelProperty("文件路径")
    private String file;
    /** 创建人ID */
    @ApiModelProperty("创建人id")
    private Integer userId;
    /** 文件类型 */
    @ApiModelProperty("文件类型")
    private String type;
    /** 文件大小 */
    @ApiModelProperty("文件大小")
    private Double size;
    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private String crateTime;
    /** 修改时间 */
    @ApiModelProperty("修改时间")
    private String updateTime;
    @ApiModelProperty("父亲目录id")
    private Integer folderId;
    @ApiModelProperty("根目录id")
    private Integer rootFolderId;
    @ApiModelProperty("是否在回收站")
    private Boolean delete;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("文件类别")
    private String category;

    // 分享的天数
    @ApiModelProperty("分享的天数")
    private Integer days;

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(String crateTime) {
        this.crateTime = crateTime;
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

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}