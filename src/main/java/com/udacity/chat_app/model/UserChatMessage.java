package com.udacity.chat_app.model;



public class UserChatMessage {
    private String msg;
    private String username;
    private MessageType type;

    
    @Override
    public String toString() {
        return "user: " + username + " says " + " : " + msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    
}
