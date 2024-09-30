package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
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
    public List<BookRetentionModel> getBookWithRetention(Integer id){

        if(id > 0)
        {

            Books book = bookRepository.findById(id)
                    .orElseThrow(()-> new BookNotFoundException("Book not found with id: "+id));
            return Collections.singletonList(createBookWithRetentionModel(book));

        }
        else{
            List<Books>allBooks = bookRepository.findAll();
            return allBooks.stream()
                    .map(this::createBookWithRetentionModel)
                    .collect(Collectors.toList());
        }

    }


    private BookRetentionModel createBookWithRetentionModel(Books id){
        BookModel bookModel = bookMapper.toModel(id);
        List<RetentionEntity> retentionEntities = retentionRepository.findByBook(id);
        List<RetentionModel> retentionModels = retentionEntities.stream()
                .map(retentionMapper::toModel)
                .collect(Collectors.toList());

        return new BookRetentionModel(bookModel,retentionModels);
    }

}