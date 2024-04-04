package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "活动类")
public class Activity {

    /** ID */
    @ApiModelProperty("活动id")
    private Integer id;
    /** 活动名称 */
    @ApiModelProperty("活动名称")
    private String name;
    /** 活动简介 */
    @ApiModelProperty("活动简介")
    private String descr;
    @ApiModelProperty("开始时间")
    /** 开始时间 */
    private String start;
    /** 结束时间 */
    @ApiModelProperty("结束时间")
    private String end;
    /** 活动形式 */
    @ApiModelProperty("活动形式")
    private String form;
    /** 活动地址 */
    @ApiModelProperty("活动地址")
    private String address;
    /** 主办方 */
    @ApiModelProperty("主办方")
    private String host;
    /** 浏览量 */
    @ApiModelProperty("浏览量")
    private Integer readCount;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("是否结束")
    private Boolean isEnd;   // 活动是否结束
    @ApiModelProperty("是否报名")
    private Boolean isSign;  // 是否报名
    @ApiModelProperty("点赞数")
    private Integer likesCount;
    @ApiModelProperty("收藏数")
    private Integer collectCount;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;
    @ApiModelProperty("是否收藏")
    private Boolean isCollect;
    @ApiModelProperty("用户id")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public Boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Boolean isCollect) {
        this.isCollect = isCollect;
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

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public Boolean getIsSign() {
        return this.isSign;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Boolean getIsEnd() {
        return this.isEnd;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
