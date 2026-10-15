/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package app.classmanagementappclient.Frontend.StatisticsWindow.Chart.DataValidate.Contract;

import app.classmanagementappclient.Frontend.Exception.InvalidDataException;

/**
 *
 * @author BEN
 */
@FunctionalInterface
public interface ChartValidatorContract {
    void validate() throws InvalidDataException;
}
