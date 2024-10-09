package springdemo.databasejdbc.service;

import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.model.RetentionModel;
import java.util.List;

public interface ServiceRetention {


     public RetentionModel createRetention(RetentionModel retentionModel, Integer userId);
     RetentionModel updateRetention(int id, RetentionModel retentionModel) ;
     List<RetentionModel> getAllRetention();
     RetentionModel getRetentionById(int id);
     void deleteRetention(int id);
}
