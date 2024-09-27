package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.RetentionModel;

import java.util.List;

public interface ServiceRetention {



    public RetentionModel createRetention(RetentionModel retentionModel);
    public RetentionModel updateRetention(long id, RetentionModel retentionModel) ;
    public List<RetentionModel> getAllRetention();
    public RetentionModel getRetentionById(long id);
    public void deleteRetention(Long id);
}
