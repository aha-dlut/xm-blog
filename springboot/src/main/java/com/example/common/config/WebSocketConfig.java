package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*
@author TanCheng
@create 2024 -03 -15    
*/
@Configuration
public class WebSocketConfig {

//    注入一个serverEndpointexporter
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
