package com.csys.template.dtoRequest;


public class ChatMessageRequest {
    
    private Integer chatRoomId;
    private Integer senderId;
    private String content;
    public ChatMessageRequest(Integer chatRoomId, Integer senderId, String content) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
    }
    public ChatMessageRequest() {
    }
    public Integer getChatRoomId() {
        return chatRoomId;
    }
    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}