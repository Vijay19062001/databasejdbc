package springdemo.databasejdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springdemo.databasejdbc.entities.RetentionEntity;

@Repository
public interface RetentionRepository extends JpaRepository<RetentionEntity, Long> {

}
