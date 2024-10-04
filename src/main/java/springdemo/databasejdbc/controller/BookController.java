package springdemo.databasejdbc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.exception.basicexception.BasicValidationException;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.model.BookModel;

import springdemo.databasejdbc.service.servicesimpl.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

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

            throw new BookNotFoundException("Books with id " + id + " not founds");
        }
        return book;
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) // Set status to 201 Created
    public @ResponseBody BookModel createBook(@Valid @RequestBody BookModel bookModel) throws BasicValidationException {

        return bookService.createBook(bookModel);

    }


    // Update an existing book by ID
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK) // Set status to 200 OK for successful updates
    public BookModel updateBook(@PathVariable Long id, @Valid @RequestBody BookModel bookModel) throws BasicValidationException {

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
    public ResponseEntity<Page<Books>> getAllBooks(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir)
    {

        pageNo = (pageNo != null) ? pageNo : 0;
        pageSize = (pageSize != null) ? pageSize : 10;
        sortBy = (sortBy != null) ? sortBy : "id";
        sortDir = (sortDir != null) ? sortDir : "asc";

        Page<Books> book = bookService.getBooksWithPagingAndSorting(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(book, HttpStatus.OK);
}
}
