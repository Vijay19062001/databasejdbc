package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.RetentionModel;
import java.util.List;

public interface ServiceRetention {


     RetentionModel createRetention(RetentionModel retentionModel);
     RetentionModel updateRetention(int id, RetentionModel retentionModel) ;
     List<RetentionModel> getAllRetention();
     RetentionModel getRetentionById(int id);
     void deleteRetention(int id);
}
