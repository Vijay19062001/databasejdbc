package springdemo.databasejdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.enums.DBStatus;

import java.util.List;

@Repository
public interface RetentionRepository extends JpaRepository<RetentionEntity, Integer> {

    List<RetentionEntity>findByBook(Books id);

   List<RetentionEntity>findByDbStatus(DBStatus dbStatus);

}
