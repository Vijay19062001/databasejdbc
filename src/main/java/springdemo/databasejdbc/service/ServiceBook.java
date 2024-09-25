package springdemo.databasejdbc.service;


import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.model.BookModel;

import java.util.List;

public interface ServiceBook {

    public List<BookModel> getAllBooks();
    public BookModel getBookById(Long id);

   public BookModel createBook(BookModel bookModel);

    public BookModel updateBook(Long id, BookModel bookModel);
    public void deleteBook(Long id);
}
