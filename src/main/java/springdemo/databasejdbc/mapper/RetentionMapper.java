package springdemo.databasejdbc.mapper;


import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.enums.RetentionStatus;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.utils.DateUtils;


import java.time.LocalDate;

@Component
public class RetentionMapper {

    public RetentionEntity toEntity(RetentionModel retentionModel, Books book) {
        RetentionEntity retentionEntity = new RetentionEntity();

        retentionEntity.setBook(book);
        retentionEntity.setName(retentionModel.getName());
        LocalDate borrowDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getBorrowDate()));
        retentionEntity.setBorrowDate(LocalDate.parse(String.valueOf(borrowDate)));
        LocalDate returnDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getReturnDate()));
        retentionEntity.setReturnDate(LocalDate.parse(String.valueOf(LocalDate.parse(String.valueOf(returnDate)))));

        LocalDate createdDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getCreatedDate()));
        retentionEntity.setCreatedDate(LocalDate.parse(String.valueOf(createdDate)));
        retentionEntity.setCreatedDate(LocalDate.parse(String.valueOf(LocalDate.from(createdDate))));

        LocalDate updatedDate = DateUtils.stringToLocalDate(String.valueOf(retentionModel.getUpdatedDate()));
        retentionEntity.setUpdatedDate(LocalDate.parse(String.valueOf(updatedDate)));
        retentionEntity.setUpdatedDate(LocalDate.parse(String.valueOf(LocalDate.from(updatedDate))));

        retentionEntity.setReturns(RetentionStatus.valueOf(retentionModel.getReturns().toUpperCase()));
        retentionEntity.setDbStatus(DBStatus.valueOf(retentionModel.getDbStatus().toUpperCase()));

        return retentionEntity;
    }

    public RetentionModel toModel(RetentionEntity retentionEntity) {
        RetentionModel retentionModel = new RetentionModel();
        Books book = new Books();

        retentionModel.setId(String.valueOf(book.getId()));
        retentionModel.setBookId(String.valueOf(retentionEntity.getBook().getId()));
        retentionModel.setName(retentionEntity.getName());
        String formattedBorrowDate = DateUtils.localDateToString(retentionEntity.getBorrowDate());
        retentionModel.setBorrowDate(formattedBorrowDate);
        String formattedReturnDate = DateUtils.localDateToString(retentionEntity.getReturnDate());
        retentionModel.setReturnDate(formattedReturnDate);

        String formattedCreatedDate = DateUtils.localDateToString(retentionEntity.getCreatedDate());
        retentionModel.setCreatedDate(formattedCreatedDate);
        String formattedUpdatedDate = DateUtils.localDateToString(retentionEntity.getUpdatedDate());
        retentionModel.setUpdatedDate(formattedUpdatedDate);

        retentionModel.setReturns(retentionEntity.getReturns().toString());
        retentionModel.setDbStatus(retentionEntity.getDbStatus().toString());

        return retentionModel;
    }
}