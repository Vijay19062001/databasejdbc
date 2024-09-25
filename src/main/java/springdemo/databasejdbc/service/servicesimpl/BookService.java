package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import springdemo.databasejdbc.model.BookModel;

import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.exception.basicexception.BookNotFoundException;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.service.ServiceBook;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements ServiceBook {



    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

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
        Book book = bookMapper.toEntity(bookModel);
        Book saved = bookRepository.save(book);

        return bookMapper.toModel(saved);
    }

    @Override
    public BookModel updateBook(Long id, BookModel bookModel) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

            book.setBookName(bookModel.getBookName());
            book.setAuthor(bookModel.getAuthor());
            book.setPublishDate(LocalDate.parse(bookModel.getPublishDate()));
            book.setPrices(Double.valueOf(bookModel.getPrices()));
            book.setPublisherCompany(bookModel.getPublisherCompany());


            Book updatesBook = bookRepository.save(book);

            return bookMapper.toModel(updatesBook);

    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
