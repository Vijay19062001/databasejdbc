package springdemo.databasejdbc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.entities.RetentionEntity;
import springdemo.databasejdbc.exception.basicexception.BasicValidationException;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.exception.basicexception.EmailSendException;
import springdemo.databasejdbc.model.BookModel;

import springdemo.databasejdbc.model.UserModel;
import springdemo.databasejdbc.service.servicesimpl.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@Validated
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(@Lazy  BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookModel> getAllBooks() {
        return bookService.getAllBooks(); // Return list of books directly
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookModel getBookById(@PathVariable Long id) {
        BookModel book = bookService.getBookById(id);
        if (book == null) {

            throw new BookNotFoundException("Book with id " + id + " not founds");
        }
        return book;
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) // Set status to 201 Created
    public BookModel createBook(@Valid @RequestBody BookModel bookModel, RetentionEntity retentionEntity) throws BasicValidationException {
        return bookService.createBook(bookModel,retentionEntity);
    }

    // Update an existing book by ID
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK) // Set status to 200 OK for successful updates
    public BookModel updateBook(@PathVariable Long id, @Valid @RequestBody BookModel bookModel ) throws BasicValidationException {

        BookModel updatedBook = bookService.updateBook(id, bookModel);

        // If the book is not found, throw an exception
        if (updatedBook == null) {
            throw new BookNotFoundException("Books with id " + id + " not found");
        }
        // Return the updated book (with 200 OK status)
        return updatedBook;

    }

    // Delete a book by ID
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        BookModel book = bookService.getBookById(id);

        if (book == null) {
            throw new BookNotFoundException("Books with id " + id + " not found");
        }

        bookService.deleteBook(id);
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<BookModel>> getAllBooks(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String authorName)
    {
        pageNo = (pageNo != null) ? pageNo : 0;
        pageSize = (pageSize != null) ? pageSize : 5;
        sortBy = (sortBy != null) ? sortBy : "id";
        sortDir = (sortDir != null) ? sortDir : "asc";

        Page<BookModel> bookPage = bookService.getBooksWithPagingAndSorting(pageNo, pageSize, sortBy, sortDir, searchText, authorName);
        return new ResponseEntity<>(bookPage, HttpStatus.OK);

    }

    @PostMapping("/{id}/send-retention-email")
    public ResponseEntity<String> sendRetentionEmail(@PathVariable int id) {
        try {
            bookService.sendBookRetentionEmail(id);  // Call the service method to send the email
            return ResponseEntity.status(HttpStatus.OK).body("Retention email sent successfully for book ID: " + id);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EmailSendException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

}




