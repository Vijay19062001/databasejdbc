package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;

import java.time.LocalDate;
import java.util.List;

public interface ServiceBookRetention {

    public List<BookRetentionModel> getBooksWithRetentions(Long id) ;
    public List<RetentionModel> getAllBookRecordRetentions(String status);
    public List<BookRetentionModel> getBooksWithDateRecords(Long id, LocalDate startDate, LocalDate endDate) ;

}
