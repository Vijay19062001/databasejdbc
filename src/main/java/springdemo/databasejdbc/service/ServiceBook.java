package springdemo.databasejdbc.service;

import org.springframework.data.domain.Page;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.model.BookModel;

import java.util.List;

public interface ServiceBook {

    public List<BookModel> getAllBooks();
    public BookModel getBookById(Long id);
   public BookModel createBook(BookModel bookModel);
    public BookModel updateBook(Long id, BookModel bookModel);
    public void deleteBook(Long id);
    public Page<Books> getBooksWithPagingAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortDir) ;


    }
