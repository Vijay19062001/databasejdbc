package springdemo.databasejdbc.service;

import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;
import java.time.LocalDate;
import java.util.List;

public interface ServiceBookRetention {

     List<BookRetentionModel> getBooksWithRetentions(Long id) ;
     List<RetentionModel> getAllBookRecordRetentions(String status);
     List<BookRetentionModel> getBooksWithDateRecords(Long id, LocalDate startDate, LocalDate endDate) ;

}
