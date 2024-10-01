package springdemo.databasejdbc.mapper;

import org.springframework.stereotype.Component;
import springdemo.databasejdbc.entities.Books;
import springdemo.databasejdbc.model.BookModel;
import springdemo.databasejdbc.enums.BookStatus;
import springdemo.databasejdbc.exception.InvalidDateFormatException;
import springdemo.databasejdbc.utils.DateUtils;


import java.time.LocalDate;


@Component
public class BookMapper {



    public Books toEntity(BookModel bookModel) throws InvalidDateFormatException {
        Books book = new Books();

        book.setBookName(bookModel.getBookName());
        book.setAuthor(bookModel.getAuthor());

        LocalDate publishDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getPublishDate()));
        book.setPublishDate(LocalDate.parse(String.valueOf(publishDate)));
        book.setPublishDate(LocalDate.parse(String.valueOf(LocalDate.from(publishDate))));

        book.setPrices(Double.valueOf(bookModel.getPrices()));
        book.setPublisherCompany(bookModel.getPublisherCompany());

        LocalDate createdDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getCreatedDate()));
        book.setCreatedDate(LocalDate.parse(String.valueOf(createdDate)));
        book.setCreatedDate(LocalDate.parse(String.valueOf(LocalDate.from(createdDate))));

        LocalDate updatedDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getUpdatedDate()));
        book.setUpdatedDate(LocalDate.parse(String.valueOf(updatedDate)));
        book.setUpdatedDate(LocalDate.parse(String.valueOf(LocalDate.from(updatedDate))));

        book.setPublishMonth(bookModel.getPublishMonth());
        book.setStatus(BookStatus.valueOf(bookModel.getStatus().toUpperCase()));


        return book;
    }

    public BookModel toModel(Books book){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(book.getBookName());
        bookModel.setAuthor(book.getAuthor());
        bookModel.setPrices(String.valueOf(book.getPrices()));
        String formattedPublishDate = DateUtils.localDateToString(book.getPublishDate());
        bookModel.setPublishDate(formattedPublishDate);
        bookModel.setPublisherCompany(book.getPublisherCompany());
        bookModel.setStatus(book.getStatus().toString());
        String formattedCreatedDate = DateUtils.localDateToString(book.getCreatedDate());
        bookModel.setCreatedDate(formattedCreatedDate);
        String formattedUpdatedDate = DateUtils.localDateToString(book.getUpdatedDate());
        bookModel.setUpdatedDate(formattedUpdatedDate);
        bookModel.setPublishMonth(String.valueOf(book.getPublishMonth()));

        return bookModel;
    }
}
