package com.example.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(description = "评论")
public class Comment {

    /** ID */
    @ApiModelProperty("id")
    private Integer id;
    /** 内容 */
    @ApiModelProperty("内容")
    private String content;
    /** 评论人 */
    @ApiModelProperty("评论人的id")
    private Integer userId;
    /** 父级ID */
    @ApiModelProperty("父级评论id")
    private Integer pid;
    /** 根节点ID */
    @ApiModelProperty("根节点的id")
    private Integer rootId;
    /** 评论时间 */
    @ApiModelProperty("评论时间")
    private String time;


    @ApiModelProperty("博客id")
    /** 博客ID */
    private Integer fid;

    @ApiModelProperty("我也不知道")
    private String module;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("回复给谁，那个人的名字")
    private String replyUser;  // 回复给谁 就是谁的名称

    @ApiModelProperty("子评论表")
    private List<Comment> children;

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
