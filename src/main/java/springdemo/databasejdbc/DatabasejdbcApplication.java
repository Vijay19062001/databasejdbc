package springdemo.databasejdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class DatabasejdbcApplication {
	public static void main(String[] args) {

		SpringApplication.run(DatabasejdbcApplication.class, args);
	}

}
