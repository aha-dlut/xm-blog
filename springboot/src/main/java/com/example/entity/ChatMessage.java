package com.example.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
@author TanCheng
@create 2024 -03 -15    
*/
public class ChatMessage {
    private String username;
    private String avatar;
    private String content;

    // constructor, getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
