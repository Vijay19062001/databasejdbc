package springdemo.databasejdbc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int id;

    @Column(name = "api_name",nullable = false)
    private String name;

    @Column(name = "log_timestamp",nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "user_name",nullable = false)
    private String userName;

    @Column(name ="errors",nullable = false)
    private String errors;

    public AuditLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AuditLog(int id, String name, LocalDateTime timeStamp, String userName, String errors) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.userName = userName;
        this.errors = errors;
    }

}
