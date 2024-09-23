package springdemo.databasejdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springdemo.databasejdbc.entities.Book;

@Repository
    public interface BookRepository extends JpaRepository<Book, Long> {
    }

