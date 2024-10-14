package springdemo.databasejdbc.service.servicesimpl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.entities.Users;
import org.slf4j.Logger;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetentionService implements ServiceRetention {

    private final RetentionRepository retentionRepository;
    private final BookRepository bookRepository;
    private final RetentionMapper retentionMapper;
    private final UserRepository userRepository;
    private final AuditService auditService;

    @Autowired
    public RetentionService(RetentionRepository retentionRepository,
                            BookRepository bookRepository,
                            RetentionMapper retentionMapper,
                            UserRepository userRepository, AuditService auditService) {
        this.retentionRepository = retentionRepository;
        this.bookRepository = bookRepository;
        this.retentionMapper = retentionMapper;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    private static final Logger logger = LoggerFactory.getLogger(RetentionService.class);

    @Override
    public RetentionModel createRetention(RetentionModel retentionModel, Integer userId) {
        logger.info("Create Retention Books");
        List<Users> users = userRepository.findByUserId(userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        Books book = bookRepository.findById(Long.valueOf(retentionModel.getBookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + retentionModel.getBookId()));

        RetentionEntity retentionEntity = retentionMapper.toEntity(retentionModel, users.get(0), book);

        RetentionEntity savedRetentionEntity = retentionRepository.save(retentionEntity);

        auditService.AuditLog(
                retentionEntity .getName(),
                "API Book Creation",
                "Created Successfully",
                ZonedDateTime.now()
        );


logger.info("Retention Book created successfully") ;
return retentionMapper.toModel(savedRetentionEntity);
    }


    @Override
    public RetentionModel updateRetention(int id, RetentionModel retentionModel) {
        logger.info("Attempting to update retention book with id: {}", id);

        RetentionEntity retentionEntity = retentionRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Retention book with id {} not found", id);
                    return new BookNotFoundException("Book with id " + id + " not found");
                });

        String originalName = retentionEntity.getName();

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
            logger.error("Invalid date format for retention book with id {}: {}", id, e.getMessage());
            throw new IllegalArgumentException("Invalid date format. Expected format is yyyyMMdd.", e);
        }

        retentionEntity.setDbStatus(DBStatus.valueOf(retentionModel.getDbStatus().toUpperCase()));
        retentionEntity.setReturns(RetentionStatus.valueOf(retentionModel.getReturns().toUpperCase()));

        // Save the updated entity
        RetentionEntity updatedEntity = retentionRepository.save(retentionEntity);
        logger.info("Retention book with id {} updated successfully", id);

        auditService.AuditLog(
                originalName,
                "API Retention Update",
                "Updated Successfully",
                ZonedDateTime.now()
        );

        return retentionMapper.toModel(updatedEntity);
    }

    @Override
    public List<RetentionModel> getAllRetention() {
        logger.info("Fetching all retention books from the repository");

        List<RetentionModel> retentionModels = retentionRepository.findAll().stream()
                .map(retentionMapper::toModel)
                .collect(Collectors.toList());

        if (retentionModels.isEmpty()) {
            logger.warn("No retention books found in the repository");
        } else {
            logger.info("Successfully fetched {} retention books", retentionModels.size());
        }

        return retentionModels;
    }


    @Override
    public RetentionModel getRetentionById(int id) {
        logger.info("Fetching RetentionModel with ID: {}", id);

        return retentionRepository.findById(id)
                .map(retention -> {
                    RetentionModel model = retentionMapper.toModel(retention);
                    logger.info("Successfully fetched RetentionModel: {}", model);
                    return model;
                })
                .orElseGet(() -> {
                    logger.warn("RetentionModel with ID: {} not found", id);
                    return null;
                });
    }

    @Override
    public void deleteRetention(int id) {
        try {
            retentionRepository.deleteById(id);
            logger.info("Retention with ID {} deleted successfully.", id);
        } catch (Exception e) {
            logger.error("Error deleting retention with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }



}
