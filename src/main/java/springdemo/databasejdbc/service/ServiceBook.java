package springdemo.databasejdbc.service;

import org.springframework.data.domain.Page;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.model.BookModel;
import java.util.List;

public interface ServiceBook {

     List<BookModel> getAllBooks();
     BookModel getBookById(Long id);
     BookModel createBook(BookModel bookModel);
     BookModel updateBook(Long id, BookModel bookModel);
     void deleteBook(Long id);
     Page<BookModel> getBooksWithPagingAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchText,String authorName) ;
     List<Books> searchBooks(String bookName) ;


    }
