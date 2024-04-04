package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(description = "网盘文件分享")
public class Share implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 名称 */
    @ApiModelProperty("名称")
    private String name;
    /** 文件ID */
    @ApiModelProperty("文件的id")
    private Integer fileId;
    @ApiModelProperty("文件类型")
    private String type;
    /** 分享时间 */
    @ApiModelProperty("分享时间")
    private String shareTime;
    /** 到期时间 */
    @ApiModelProperty("到期时间")
    private String endTime;
    /** 访问次数 */
    @ApiModelProperty("访问次数")
    private Integer count;
    /** 分享人ID */
    @ApiModelProperty("分享人的id")
    private Integer userId;
    @ApiModelProperty("分享码")
    private String code;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("状态")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
