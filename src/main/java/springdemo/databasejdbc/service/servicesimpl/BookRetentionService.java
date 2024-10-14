package springdemo.databasejdbc.service.servicesimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.repository.RetentionRepository;
import springdemo.databasejdbc.service.ServiceBookRetention;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookRetentionService implements ServiceBookRetention {

    private final BookRepository bookRepository;
    private final RetentionMapper retentionMapper;
    private final RetentionRepository retentionRepository;

    // Constructor injection
    @Autowired
    public BookRetentionService(BookRepository bookRepository,
                                RetentionMapper retentionMapper,
                                RetentionRepository retentionRepository) {
        this.bookRepository = bookRepository;
        this.retentionMapper = retentionMapper;
        this.retentionRepository = retentionRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);


    @Override
    public List<BookRetentionModel> getBooksWithRetentions(Long id) {
        logger.info("Fetching books with retentions for book id: {}", id);

        List<Books> books;
        if (id == null || id == 0) {
            logger.info("No specific book id provided, fetching all books.");
            books = bookRepository.findAll();
        } else {
            logger.info("Fetching book with id: {}", id);
            books = Collections.singletonList(
                    bookRepository.findById(id)
                            .orElseThrow(() -> {
                                logger.error("Book not found with id: {}", id);
                                return new BookNotFoundException("Book not found with id: " + id);
                            })
            );
        }

        List<BookRetentionModel> bookRetentionModels = books.stream()
                .flatMap(book -> {
                    logger.info("Fetching retention records for book id: {}", book.getId());
                    List<RetentionEntity> retentionEntities = retentionRepository.findByBook(book);
                    return retentionEntities.stream()
                            .map(retention -> new BookRetentionModel(
                                    book.getId(),
                                    book.getBookName(),
                                    retentionMapper.toModel(retention)
                            ));
                })
                .collect(Collectors.toList());

        logger.info("Successfully fetched {} book(s) with retentions", bookRetentionModels.size());
        return bookRetentionModels;
    }


    @Override
    public List<RetentionModel> getAllBookRecordRetentions(String status) {
        List<RetentionEntity> retentionEntities;
        if (status == null || status.equalsIgnoreCase("all")) {
            retentionEntities = retentionRepository.findAll();
        } else if (status.equalsIgnoreCase("active")) {
            retentionEntities = retentionRepository.findByDbStatus(DBStatus.ACTIVE);
        } else if (status.equalsIgnoreCase("inactive")) {
            retentionEntities = retentionRepository.findByDbStatus(DBStatus.INACTIVE);
        } else {
            throw new IllegalArgumentException("Invalid status. Use 'all', 'active', or 'inactive'.");
        }

        return retentionEntities.stream()
                .map(retentionMapper::toModel)
                .collect(Collectors.toList());

    }


    @Override
    public List<BookRetentionModel> getBooksWithDateRecords(Long id, LocalDate borrowDate, LocalDate returnDate) {
        logger.info("Fetching books with retention records. Book ID: {}, Borrow Date: {}, Return Date: {}", id, borrowDate, returnDate);

        List<Books> books;
        if (id == null || id == 0) {
            logger.info("No specific book ID provided. Fetching all books.");
            books = bookRepository.findAll();
        } else {
            logger.info("Fetching book with ID: {}", id);
            books = Collections.singletonList(bookRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Book not found with id: {}", id);
                        return new BookNotFoundException("Book not found with id: " + id);
                    }));
        }

        List<BookRetentionModel> bookRetentionModels = books.stream()
                .flatMap(book -> {
                    List<RetentionEntity> retentionEntities = retentionRepository.findByBook(book);
                    logger.debug("Found {} retention records for book ID: {}", retentionEntities.size(), book.getId());

                    return retentionEntities.stream()
                            .filter(retention -> retention.getDbStatus() == DBStatus.ACTIVE &&
                                    (borrowDate == null || !retention.getBorrowDate().isBefore(borrowDate)) &&
                                    (returnDate == null || !retention.getBorrowDate().isAfter(returnDate))
                            )
                            .map(retention -> {
                                logger.debug("Mapping retention record to BookRetentionModel for book ID: {}", book.getId());
                                return new BookRetentionModel(
                                        book.getId(),
                                        book.getBookName(),
                                        retentionMapper.toModel(retention)
                                );
                            });
                })
                .collect(Collectors.toList());

        logger.info("Successfully retrieved {} book retention records", bookRetentionModels.size());
        return bookRetentionModels;
    }


}


