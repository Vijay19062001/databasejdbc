package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.enums.RetentionStatus;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.repository.RetentionRepository;
import springdemo.databasejdbc.repository.UserRepository;
import springdemo.databasejdbc.service.ServiceRetention;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetentionService implements ServiceRetention {

    private final RetentionRepository retentionRepository;
    private final BookRepository bookRepository;
    private final RetentionMapper retentionMapper;
    private final UserRepository userRepository;

    @Autowired // This is optional for a single constructor in Spring 4.3+
    public RetentionService(RetentionRepository retentionRepository,
                            BookRepository bookRepository,
                            RetentionMapper retentionMapper,
                            UserRepository userRepository) {
        this.retentionRepository = retentionRepository;
        this.bookRepository = bookRepository;
        this.retentionMapper = retentionMapper;
        this.userRepository = userRepository;
    }



    @Override
    public RetentionModel createRetention(RetentionModel retentionModel, Integer userId) {
        // Fetch the user based on userId
        List<Users> users = userRepository.findByUserId(userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        // Fetch the book entity from the database
        Books book = bookRepository.findById(Long.valueOf(retentionModel.getBookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + retentionModel.getBookId()));

        // Map the RetentionModel to RetentionEntity
        RetentionEntity retentionEntity = retentionMapper.toEntity(retentionModel, users.get(0), book);

        // Save retention entity
        RetentionEntity savedRetentionEntity = retentionRepository.save(retentionEntity);

        // Return the mapped model
        return retentionMapper.toModel(savedRetentionEntity);
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
