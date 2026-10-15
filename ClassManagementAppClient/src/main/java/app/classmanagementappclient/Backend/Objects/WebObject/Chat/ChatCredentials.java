/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject.Chat;

import java.util.UUID;

/**
 *
 * @author BEN
 */
public class ChatCredentials {
    
    private final String uuid = UUID.randomUUID().toString();
    private final String chatName;
    private int chatID;

    public ChatCredentials(String chatName) {
        this.chatName = chatName;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getUuid() {
        return uuid;
    }

    public String getChatName() {
        return chatName;
    }

    @Override
    public String toString() {
        return "ChatCredentials{" + "uuid=" + uuid + ", chatName=" + chatName + ", chatID=" + chatID + '}';
    }
}
