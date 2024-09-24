package springdemo.databasejdbc.mapper;

import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Book;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.exception.InvalidDateFormatException;
import springdemo.databasejdbc.utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BookMapper {



    public Book toEntity(BookModel bookModel) throws InvalidDateFormatException {
        Book book = new Book();
        book.setBookName(bookModel.getBookName());
        book.setAuthor(bookModel.getAuthor());

        // publishDate from String to LocalDate
        LocalDate publishDate = DateUtils.stringToLocalDate(bookModel.getPublishDate());
        book.setPublishDate(publishDate);

        book.setPublishDate(LocalDate.from(publishDate));
        book.setPrices(Double.valueOf(bookModel.getPrices()));
        book.setPublisherCompany(bookModel.getPublisherCompany());


        book.setPublishMonth(book.getPublishMonth());
        // Set status based on the book model input
        book.setStatus(BookStatus.valueOf(bookModel.getStatus().toUpperCase()));

        return book;
    }

    public BookModel toModel(Book book){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(book.getBookName());
        bookModel.setAuthor(book.getAuthor());
        bookModel.setPrices(String.valueOf(book.getPrices()));

        String formattedPublishDate = DateUtils.localDateToString(book.getPublishDate());
        bookModel.setPublishDate(formattedPublishDate);

        bookModel.setPublisherCompany(book.getPublisherCompany());

        // Set status as String
        bookModel.setStatus(book.getStatus().toString());


        return bookModel;
    }
}
