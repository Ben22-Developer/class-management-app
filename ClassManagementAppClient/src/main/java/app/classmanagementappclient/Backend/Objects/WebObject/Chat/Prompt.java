/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject.Chat;

/**
 *
 * @author BEN
 */
public class Prompt {
    
    private final String prompt;
    private final String chatID;

    public Prompt(String prompt, String chatID) {
        this.prompt = prompt;
        this.chatID = chatID;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getChatID() {
        return chatID;
    }

    @Override
    public String toString() {
        return "Prompt{" + "prompt=" + prompt + '}';
    }
}