package springdemo.databasejdbc.service;

import org.springframework.data.domain.Page;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.entities.Users;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.model.UserModel;

import java.util.List;

public interface ServiceBook {

     List<BookModel> getAllBooks();
     BookModel getBookById(Long id);
     public BookModel createBook(BookModel bookModel, RetentionEntity retentionEntity) ;
     BookModel updateBook(Long id, BookModel bookModel);
     void deleteBook(Long id);
     Page<BookModel> getBooksWithPagingAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortDir,String searchText,String authorName) ;
     List<Books> searchBooks(String bookName) ;
     public void sendBookRetentionEmail(int id);

     }
