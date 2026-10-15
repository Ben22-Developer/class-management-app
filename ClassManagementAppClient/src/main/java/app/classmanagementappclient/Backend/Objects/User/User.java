/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.User;

import app.classmanagementappclient.Backend.Objects.Enum.TokenCategory;

/**
 *
 * @author BEN
 */
public record User(int id, String username, String password, TokenCategory tokenCategory) {}
