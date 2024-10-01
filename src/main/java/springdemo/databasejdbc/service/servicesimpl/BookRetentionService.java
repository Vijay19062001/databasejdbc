package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.model.BookRetentionModel;
import springdemo.databasejdbc.model.RetentionModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.repository.RetentionRepository;
import springdemo.databasejdbc.service.ServiceBookRetention;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRetentionService implements ServiceBookRetention {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RetentionMapper retentionMapper;

    @Autowired
    private RetentionRepository retentionRepository;

        @Override
        public List<BookRetentionModel> getBooksWithRetentions(Long id) {
            List<Books> books;
            if (id == null || id == 0) {
                books = bookRepository.findAll();
            } else {
                books = Collections.singletonList(bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id)));
            }

            return books.stream()
                    .flatMap(book -> {
                        List<RetentionEntity> retentionEntities = retentionRepository.findByBook(book);
                        return retentionEntities.stream()
                                .map(retention -> new BookRetentionModel(
                                        book.getId(),
                                        book.getBookName(),
                                        retentionMapper.toModel(retention)
                                ));
                    })
                    .collect(Collectors.toList());
        }



    @Override
    public List<RetentionModel> getAllBookRecordRetentions(String status)
    {
        List<RetentionEntity> retentionEntities;
        if (status == null || status.equalsIgnoreCase("all"))
        {
            retentionEntities = retentionRepository.findAll();
        }
        else if (status.equalsIgnoreCase("active"))
        {
            retentionEntities = retentionRepository.findByDbStatus(DBStatus.ACTIVE);
        }
        else if (status.equalsIgnoreCase("inactive"))
        {
            retentionEntities = retentionRepository.findByDbStatus(DBStatus.INACTIVE);
        }
        else
        {
            throw new IllegalArgumentException("Invalid status. Use 'all', 'active', or 'inactive'.");
        }

        return retentionEntities.stream()
                .map(retentionMapper::toModel)
                .collect(Collectors.toList());

    }

}
