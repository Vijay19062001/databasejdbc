package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.mapper.RetentionMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.service.ServiceBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements ServiceBook {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RetentionMapper retentionMapper;


    @Override
    public List<BookModel> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public BookModel getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toModel)
                .orElse(null);
    }

    @Override
    public BookModel createBook(BookModel bookModel) {
        Books book = bookMapper.toEntity(bookModel);
        Books saved = bookRepository.save(book);

        return bookMapper.toModel(saved);
    }

    @Override
    public BookModel updateBook(Long id, BookModel bookModel) {
        Books book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Books with id " + id + " not found"));

            book.setBookName(bookModel.getBookName());
            book.setAuthor(bookModel.getAuthor());
            book.setPrices(Double.valueOf(bookModel.getPrices()));
            book.setPublisherCompany(bookModel.getPublisherCompany());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try{
            LocalDate publishDate = LocalDate.parse(bookModel.getPublishDate(),formatter);
            book.setPublishDate(publishDate);

            LocalDate createdDate = LocalDate.parse(bookModel.getCreatedDate(),formatter);
            book.setCreatedDate(createdDate);

            LocalDate updatedDate = LocalDate.parse(bookModel.getUpdatedDate(),formatter);
            book.setUpdatedDate(updatedDate);

        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format.Expected format is yyyyMMdd",e);
        }
            Books updatesBooks = bookRepository.save(book);
            return bookMapper.toModel(updatesBooks);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


}
