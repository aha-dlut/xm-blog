package com.example.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.net.URLDecoder;

/*
@author TanCheng
@create 2024 -03 -15    
*/
@Component
@ServerEndpoint(value = "/chat/{username}/{avatar}")
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();



    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        WebSocketServer.redisTemplate = redisTemplate;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("avatar") String avatar){
        sessionMap.put(session.getId(),session);

        try {
            username = URLDecoder.decode(username, "UTF-8");
           avatar = URLDecoder.decode(avatar, "UTF-8");
            System.out.println(username);
            System.out.println(avatar);
        } catch (UnsupportedEncodingException e) {
            // 处理异常
        }
        //获得redis中的所有消息
//        List<ChatMessage> messages = service.getMessages();
        //向新加入的用户发送最近的所有消息
        try {
            List<String> messages = redisTemplate.opsForList().range("messages",0,29);
            if(messages.size() >0){
                Collections.reverse(messages);
                for(String message:messages){
                    session.getBasicRemote().sendText(message);
                    System.out.println(message);
                }
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端异常", e);
        }

    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        sessionMap.remove(session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session fromSession) {
        System.out.println(message);
        log.info("服务端收到消息:{}", message);
        //存储数据到redis中
        System.out.println(redisTemplate);
        redisTemplate.opsForList().leftPush("messages",message);

        //向所有用户发送新插入的这条数据
        sendAllMessage(message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[onError] 发生错误", error);
    }

    /**
     * 服务端发送消息给除了自己的其他客户端
     */
    private void sendMessage(Session fromSession, String message) {
        sessionMap.values().forEach(session -> {
            if (fromSession != session) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("服务端发送消息给客户端异常", e);
                }
            }
        });
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }


}
