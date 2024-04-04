package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "回收站")
public class Trash implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 文件ID */
    @ApiModelProperty("文件id")
    private Integer fileId;
    @ApiModelProperty("用户id")
    private Integer userId;
    /** 文件名称 */
    @ApiModelProperty("文件名")
    private String name;
    /** 文件大小 */
    @ApiModelProperty("文件大小")
    private Double size;
    /** 删除时间 */
    @ApiModelProperty("删除时间")
    private String time;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("文件类型")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
