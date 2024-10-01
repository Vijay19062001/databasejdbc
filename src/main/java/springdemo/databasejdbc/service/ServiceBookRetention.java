package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;

import java.util.List;

public interface ServiceBookRetention {

    public List<BookRetentionModel> getBooksWithRetentions(Long id) ;

    public List<RetentionModel> getAllBookRecordRetentions(String status);




}
