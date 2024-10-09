package springdemo.databasejdbc.service.servicesimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.AuditLog;
import springdemo.databasejdbc.repository.AuditLogRepository;
import springdemo.databasejdbc.service.ServiceLog;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Service
public class AuditService implements ServiceLog {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void AuditLog(String userName, String apiName, String status, ZonedDateTime timeStamp) {
        AuditLog log = new AuditLog();
        log.setUserName(userName);
        log.setName(apiName);
        log.setErrors(status); // Assuming 'errors' was meant to store the status message
        log.setTimeStamp(LocalDateTime.from(timeStamp)); // Use the provided timeStamp
        auditLogRepository.save(log);
    }

}
