/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.classmanagementappclient.Backend.Database.Read.Interfaces;

import java.util.List;

/**
 *
 * @author BEN
 */
public interface ReadDoublesOfDoublesInterface extends ReadDataInterface{
    
    @Override
    List<List<Double>> getData();
}
