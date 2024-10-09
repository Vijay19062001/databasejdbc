package springdemo.databasejdbc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "audit")
public class BookAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "time_stamp",nullable = false)
    private LocalDate timeStamp;

    @Column(name = "update",nullable = false)
    private String update;

    @Column(name ="error_log",nullable = false)
    private String errorLog;


    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
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

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public BookAudit(int id, String name, LocalDate timeStamp, String update, String errorLog) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.update = update;
        this.errorLog = errorLog;
    }

}
