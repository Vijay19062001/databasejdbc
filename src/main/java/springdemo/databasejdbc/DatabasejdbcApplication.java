package springdemo.databasejdbc;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class DatabasejdbcApplication {

	public static void main(String[] args) {

		SpringApplication.run(DatabasejdbcApplication.class, args);
	}

}
