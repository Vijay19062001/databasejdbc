package springdemo.databasejdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springdemo.databasejdbc.entities.Books;

import java.util.Optional;

@Repository
    public interface BookRepository extends JpaRepository<Books, Long> {

    Optional<Books> findById(long id);
}

