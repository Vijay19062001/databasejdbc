package springdemo.databasejdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.exception.basicexception.BasicValidationException;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.service.servicesimpl.BookService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all books
    @GetMapping("/all")
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        BookModel book = bookService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);  // Return OK status
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new book
    @PostMapping("/add")
    public ResponseEntity<BookModel> createBook(@RequestBody BookModel bookModel) throws BasicValidationException {

            bookModel.validate();

//            bookModel.setCreatedDate(String.valueOf(LocalDateTime.now()));  // Set creation date
//            bookModel.setUpdatedDate(String.valueOf(LocalDateTime.now()));  // Set updated date
            BookModel createdBook = bookService.createBook(bookModel);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        }



    // Update an existing book by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody BookModel bookModel,Book book)  {
        // Validate the updated book data
        bookModel.validate();

        // Update the book information
        BookModel updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a book by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id) != null) {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
