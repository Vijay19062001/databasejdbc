package springdemo.databasejdbc.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import springdemo.databasejdbc.model.BookModel;

import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.mapper.BookMapper;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.repository.BookRepository;
import springdemo.databasejdbc.service.ServiceBook;

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
    public BookModel updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setBookName(updatedBook.getBookName());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublishDate(updatedBook.getPublishDate());
            book.setPrices(updatedBook.getPrices());
            book.setPublisherCompany(updatedBook.getPublisherCompany());


            Book updatesBook = bookRepository.save(book);

            return bookMapper.toModel(updatesBook);
        }
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
