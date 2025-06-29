package com.udacity.chat_app.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.websocket.Session;

@Configuration
public class Users {
    

    @Bean(name = "onlineUserSessions")
    Map<String,Session> getOnlineUsers()
    {
        return new ConcurrentHashMap<String,Session>();
    }
}
