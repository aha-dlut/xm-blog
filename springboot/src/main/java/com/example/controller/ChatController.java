package com.example.controller;

import com.example.common.Result;
import com.example.entity.ChatMessage;
import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
@author TanCheng
@create 2024 -03 -15    
*/
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    public Result addMessage(@RequestBody String message){

        return Result.success();
    }

    public Result getMessages(){
        List<ChatMessage> messages = chatService.getMessages();
        return Result.success(messages);
    }
}
