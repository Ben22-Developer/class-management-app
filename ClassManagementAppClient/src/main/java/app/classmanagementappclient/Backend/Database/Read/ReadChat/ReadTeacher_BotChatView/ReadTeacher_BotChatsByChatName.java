/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.ReadChat.ReadTeacher_BotChatView;

import app.classmanagementappclient.Backend.Database.Read.Interfaces.ReadObjectOfObjectInterface;
import app.classmanagementappclient.Backend.Database.UtilityClass;
import app.classmanagementappclient.Backend.Objects.Teacher.LoggedInTeacher;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BEN
 */
public class ReadTeacher_BotChatsByChatName implements ReadObjectOfObjectInterface {


    private final String chatName;
    private final List<List<Object>> chats = new ArrayList<>();
    
    public ReadTeacher_BotChatsByChatName (String chatName) {
        this.chatName = chatName;
    }    
    
    @Override
    public String getQuery() {
        return """
                SELECT teacher_botChatView.chatID, teacher_botChatView.content, teacher_botChatView.type, teacher_botChatView.timestamp FROM teacher_botChatView
                JOIN chatUUIDView ON chatUUIDView.id = teacher_botChatView.chatID
                WHERE chatUUIDView.chatName = ? AND chatUUIDView.TeacherNum = ? ORDER BY teacher_botChatView.chatID
               """;
    }

    @Override
    public void readData(PreparedStatement statement, UtilityClass uc) throws SQLException {
        uc.getObjectsStatementByRowRes(statement, chats);
    }

    @Override
    public void setStatement(PreparedStatement statement, UtilityClass uc) throws SQLException {
        int index = uc.setStringStatement(statement, chatName, 1);
        uc.setIntStatement(statement, LoggedInTeacher.getLoggedInTeacher().getTeacherNum(), index);
    }
    
    @Override
    public List<List<Object>> getData() {
        return chats;
    }    
    
}
