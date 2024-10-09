package springdemo.databasejdbc.mapper;


import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.enums.RetentionStatus;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.utils.DateUtils;

@Component
public class RetentionMapper {

    public RetentionEntity toEntity(RetentionModel retentionModel, Users users, Books book) {
        RetentionEntity retentionEntity = new RetentionEntity();

        retentionEntity.setBook(book);

        retentionEntity.setUsers(users);
        retentionEntity.setName(retentionModel.getName());
        retentionEntity.setBorrowDate(DateUtils.stringToLocalDate(retentionModel.getBorrowDate()));
        retentionEntity.setReturnDate(DateUtils.stringToLocalDate(retentionModel.getReturnDate()));
        retentionEntity.setCreatedDate(DateUtils.stringToLocalDate(retentionModel.getCreatedDate()));
        retentionEntity.setUpdatedDate(DateUtils.stringToLocalDate(retentionModel.getUpdatedDate()));
        retentionEntity.setReturns(RetentionStatus.valueOf(retentionModel.getReturns().toUpperCase()));
        retentionEntity.setDbStatus(DBStatus.valueOf(retentionModel.getDbStatus().toUpperCase()));

        return retentionEntity;
    }

    public RetentionModel toModel(RetentionEntity retentionEntity) {
        RetentionModel retentionModel = new RetentionModel();

        // Set the user and book IDs
        retentionModel.setUserId(String.valueOf(retentionEntity.getUsers().getUserId()));
        retentionModel.setBookId(String.valueOf(retentionEntity.getBook().getId()));

        // Map the rest of the fields from retentionEntity
        retentionModel.setName(retentionEntity.getName());
        retentionModel.setBorrowDate(DateUtils.localDateToString(retentionEntity.getBorrowDate()));
        retentionModel.setReturnDate(DateUtils.localDateToString(retentionEntity.getReturnDate()));
        retentionModel.setCreatedDate(DateUtils.localDateToString(retentionEntity.getCreatedDate()));
        retentionModel.setUpdatedDate(DateUtils.localDateToString(retentionEntity.getUpdatedDate()));
        retentionModel.setReturns(retentionEntity.getReturns().toString());
        retentionModel.setDbStatus(retentionEntity.getDbStatus().toString());

        return retentionModel;
    }
}