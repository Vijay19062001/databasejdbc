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
        Books books = new Books();
        books.setBookName(bookModel.getBookName());
        books.setAuthor(bookModel.getAuthor());

        LocalDate publishDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getPublishDate()));
        books.setPublishDate(LocalDate.parse(String.valueOf(publishDate)));
        books.setPublishDate(LocalDate.parse(String.valueOf(LocalDate.from(publishDate))));

        books.setPrices(Double.valueOf(bookModel.getPrices()));
        books.setPublisherCompany(bookModel.getPublisherCompany());

        LocalDate createdDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getCreatedDate()));
        books.setCreatedDate(LocalDate.parse(String.valueOf(createdDate)));
        books.setCreatedDate(LocalDate.parse(String.valueOf(LocalDate.from(createdDate))));

        LocalDate updatedDate = DateUtils.stringToLocalDate(String.valueOf(bookModel.getUpdatedDate()));
        books.setUpdatedDate(LocalDate.parse(String.valueOf(updatedDate)));
        books.setUpdatedDate(LocalDate.parse(String.valueOf(LocalDate.from(updatedDate))));

        books.setPublishMonth(bookModel.getPublishMonth());
        books.setStatus(BookStatus.valueOf(bookModel.getStatus().toUpperCase()));


        return books;
    }

    public BookModel toModel(Books books){
        BookModel bookModel = new BookModel();
        bookModel.setBookName(books.getBookName());
        bookModel.setAuthor(books.getAuthor());
        bookModel.setPrices(String.valueOf(books.getPrices()));
        String formattedPublishDate = DateUtils.localDateToString(books.getPublishDate());
        bookModel.setPublishDate(formattedPublishDate);
        bookModel.setPublisherCompany(books.getPublisherCompany());
        bookModel.setStatus(books.getStatus().toString());
        String formattedCreatedDate = DateUtils.localDateToString(books.getCreatedDate());
        bookModel.setCreatedDate(formattedCreatedDate);
        String formattedUpdatedDate = DateUtils.localDateToString(books.getUpdatedDate());
        bookModel.setUpdatedDate(formattedUpdatedDate);
        bookModel.setPublishMonth(String.valueOf(books.getPublishMonth()));

        return bookModel;
    }
}
