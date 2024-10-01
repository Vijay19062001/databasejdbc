package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.enums.RetentionStatus;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.repository.RetentionRepository;
import springdemo.databasejdbc.service.ServiceRetention;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetentionService implements ServiceRetention {

    @Autowired
    private RetentionRepository retentionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RetentionMapper retentionMapper;


    @Override
    public RetentionModel createRetention(RetentionModel retentionModel) {
        RetentionEntity retentionEntity;

        if (retentionModel.getBookId() != null && !retentionModel.getBookId().isEmpty()) {
            try {
                Long bookId = Long.valueOf(retentionModel.getBookId());
                Books book = bookRepository.findByIdAndStatus(bookId, BookStatus.ACTIVE);

                if (book == null) {
                    throw new BookNotFoundException("Book with id " + bookId + " not found or not active");
                }

                retentionEntity = retentionMapper.toEntity(retentionModel, book);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid book ID format: " + retentionModel.getBookId(), e);
            }
        } else {

            retentionEntity = retentionMapper.toEntity(retentionModel, null);
        }

        RetentionEntity saved = retentionRepository.save(retentionEntity);
        return retentionMapper.toModel(saved);
    }


    @Override
    public RetentionModel updateRetention(int id, RetentionModel retentionModel){
        RetentionEntity retentionEntity = retentionRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Books with id " + id + " not found"));

        retentionEntity.setName(retentionModel.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            LocalDate borrowDate = LocalDate.parse(retentionModel.getBorrowDate(), formatter);
            retentionEntity.setBorrowDate(borrowDate);

            LocalDate returnDate = LocalDate.parse(retentionModel.getReturnDate(), formatter);
            retentionEntity.setReturnDate(returnDate);

            LocalDate createdDate = LocalDate.parse(retentionModel.getCreatedDate(), formatter);
            retentionEntity.setCreatedDate(createdDate);

            LocalDate updatedDate = LocalDate.parse(retentionModel.getUpdatedDate(), formatter);
            retentionEntity.setUpdatedDate(updatedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format is yyyyMMdd.", e);
        }

        retentionEntity.setDbStatus(DBStatus.valueOf(retentionModel.getDbStatus().toUpperCase()));
        retentionEntity.setReturns(RetentionStatus.valueOf(retentionModel.getReturns().toUpperCase()));

        // Save and return the updated entity
        RetentionEntity updatedEntity = retentionRepository.save(retentionEntity);
        return retentionMapper.toModel(updatedEntity);
    }


    @Override
    public List<RetentionModel> getAllRetention(){
        return retentionRepository.findAll().stream()
                .map(retentionMapper::toModel)
                .collect(Collectors.toList());
    }



    @Override
    public RetentionModel getRetentionById(int id) {
        return retentionRepository.findById(id)
                .map(retentionMapper::toModel)
                .orElse(null);
    }


    @Override
    public void deleteRetention(int id) {
        retentionRepository.deleteById(id);
    }



}
