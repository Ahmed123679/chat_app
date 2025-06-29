package com.udacity.chat_app.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.chat_app.model.ServerMessage;
import com.udacity.chat_app.model.UserChatMessage;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;

@ServerEndpoint(value="/messages",configurator = ServerWebSocket.Configurator.class)
@Component
public class ServerWebSocket {
    ObjectMapper mapper = new ObjectMapper();
    private static Map<Session,String> onlineUserSessions = new ConcurrentHashMap<>();
    

        public static class Configurator extends ServerEndpointConfig.Configurator {

        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            HttpSession session = (HttpSession)request.getHttpSession();
            System.out.println("session already established");

            String username = (String)session.getAttribute("username");
            System.out.println("user "+ username + "with session id: " + session.getId());           
            config.getUserProperties().put("username", username);

        }

    }
    
    

    
    @OnOpen
    public void onOpen(Session session,EndpointConfig config)
    {
        String username = (String)config.getUserProperties().get("username");
        ServerWebSocket.onlineUserSessions.put(session,username);
        System.out.println("user "+ username + "with session id: " + session.getId());
        
    }
    @OnClose
    public void OnClose(Session session,CloseReason reason)
    {
        onlineUserSessions.remove(session);
    }

    @OnMessage
    public void OnMessage(Session session, String userMessage)
    {
        
        try {
            UserChatMessage message;
            message = mapper.readValue(userMessage, UserChatMessage.class);
            
            ServerMessage serverMessage = new ServerMessage();
            serverMessage.setMsg(message.getMsg());
            serverMessage.setUsername(message.getUsername());
            serverMessage.setType(message.getType());
            serverMessage.setOnlineCount(onlineUserSessions.size());
        
            for(Map.Entry<Session,String> user : onlineUserSessions.entrySet())
            {
                Session userSession = user.getKey();
                String json = mapper.writeValueAsString(serverMessage);
                userSession.getBasicRemote().sendText(json);
                
                
                    
            }
        }catch(JsonMappingException json) { 
            System.out.println("can not parse the userMessage");
            

        }catch(IOException io)
        {
            System.out.println("Error sending message");
        }
    
        
    }
}
