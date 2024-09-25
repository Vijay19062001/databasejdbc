package springdemo.databasejdbc.mapper;


import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.utils.DateUtils;

import java.time.LocalDate;

@Component
public class RetentionMapper {

    public RetentionEntity toEntity(RetentionModel retentionModel) {
        RetentionEntity retentionEntity = new RetentionEntity();

        // If you have a service to fetch the Book by ID, do that here.
//         Book book = bookService.findById(retentionModel.getBookId());
//         retentionEntity.setBook(book);

        retentionEntity.setName(retentionModel.getName());

        // Parse borrowDate and returnDate from String to LocalDate
        LocalDate borrowDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getBorrowDate()));
        retentionEntity.setBorrowDate(borrowDate);

        LocalDate returnDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getReturnDate()));
        retentionEntity.setReturnDate(returnDate);

        return retentionEntity;
    }

    public RetentionModel toModel(RetentionEntity retentionEntity) {
        RetentionModel retentionModel = new RetentionModel();

        // Set the ID and Book ID
        retentionModel.setId(String.valueOf(retentionEntity.getId()));
//        retentionModel.setBookId(retentionEntity.getBook().getId());

        retentionModel.setName(retentionEntity.getName());

        // Format dates as String
        String formattedBorrowDate = DateUtils.localDateToString(retentionEntity.getBorrowDate());
        retentionModel.setBorrowDate(LocalDate.parse(formattedBorrowDate));

        String formattedReturnDate = DateUtils.localDateToString(retentionEntity.getReturnDate());
        retentionModel.setReturnDate(LocalDate.parse(formattedReturnDate));

        return retentionModel;
    }
}
