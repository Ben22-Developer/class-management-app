/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Frontend.Exception;

/**
 *
 * @author BEN
 */
public class InvalidDataException extends RuntimeException{
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException() {
        super();
    }   
}
