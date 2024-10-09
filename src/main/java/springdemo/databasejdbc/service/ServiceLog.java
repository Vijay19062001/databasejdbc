package springdemo.databasejdbc.service;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface ServiceLog {

    public void AuditLog(String userName, String apiName, String status, ZonedDateTime timeStamp) ;
}
