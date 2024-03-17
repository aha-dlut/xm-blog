package com.example.service;

import com.example.entity.Account;
import com.example.entity.ChatMessage;
import com.example.utils.TokenUtils;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
@author TanCheng
@create 2024 -03 -15    
*/
@Component
public class ChatService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addChatMessage() {
        System.out.println("测试chatservice中redistemplate是否可以使用");

    }

    public List<ChatMessage> getMessages() {
        return null;
    }


}
