/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject;

/**
 *
 * @author BEN
 */
public class PasswordResetCode 
{    
    private int id;
    private String code;
    private String username;
//    private LocalDateTime localDateTime;   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public LocalDateTime getLocalDateTime() {
//        return localDateTime;
//    }
//
//    public void setLocalDateTime(LocalDateTime localDateTime) {
//        this.localDateTime = localDateTime;
//    }

    @Override
    public String toString() {
        return "PasswordResetCode{" + "id=" + id + ", code=" + code + ", username=" + username + '}';
    }
    
    
}
