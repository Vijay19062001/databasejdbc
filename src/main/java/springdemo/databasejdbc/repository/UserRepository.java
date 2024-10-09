package springdemo.databasejdbc.repository;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdemo.databasejdbc.entities.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    List<Users>findByUserId(Integer userId);


}
