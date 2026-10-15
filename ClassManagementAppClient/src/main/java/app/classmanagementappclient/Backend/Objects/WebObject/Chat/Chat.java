/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject.Chat;

import app.classmanagementappclient.Backend.Objects.Enum.ChatType;

import java.sql.Timestamp;

/**
 *
 * @author BEN
 */
public class Chat {
    
    private final int chatID;
    private final String content;
    private final ChatType chatType;
    private final Timestamp timestamp;

    public Chat(int chatID, String content, ChatType chatType, Timestamp timestamp) {
        this.chatID = chatID;
        this.content = content;
        this.chatType = chatType;
        this.timestamp = timestamp;
    }

    public int getChatID() {
        return chatID;
    }

    public String getContent() {
        return content;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Chat{" + "chatID=" + chatID + ", content=" + content + ", chatType=" + chatType + ", timestamp=" + timestamp + '}';
    }
}
