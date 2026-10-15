/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.classmanagementappclient.Backend.Objects.WebObject.Task;

import java.time.LocalDate;

/**
 *
 * @author BEN
 */
public class Task {
    
    private int id;
    private String title;
    private String description;
    private final LocalDate localDate;

    public Task(String title, String description, LocalDate localDate) {
        this.title = title;
        this.description = description;
        this.localDate = localDate;
    } 
    
    public Task(LocalDate localDate) {
        this.localDate = localDate;
    }     

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title=" + title + ", description=" + description + ", localDate=" + localDate + '}';
    }
}
