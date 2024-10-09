package springdemo.databasejdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import springdemo.databasejdbc.entities.AuditLog;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {


}
