package springdemo.databasejdbc.mapper;

import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.model.BookModel;

@Component
public class BookMapper {

    public Book toEntity(BookModel bookModel) {
        Book book = new Book();
        book.setBookName(bookModel.getBookName());
        book.setAuthor(bookModel.getAuthor());
        book.setPublishDate(bookModel.getPublishDate());
        book.setPrices(Double.valueOf(bookModel.getPrices()));
        book.setPublisherCompany(bookModel.getPublisherCompany());

        return book;
    }

    public BookModel toModel(Book book){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(book.getBookName());
        bookModel.setAuthor(book.getAuthor());
        bookModel.setPrices(String.valueOf(book.getPrices()));
        bookModel.setPublishDate(book.getPublishDate());
        bookModel.setPublisherCompany(book.getPublisherCompany());

        return bookModel;
    }
}
