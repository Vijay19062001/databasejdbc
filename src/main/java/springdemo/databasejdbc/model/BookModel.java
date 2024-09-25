package springdemo.databasejdbc.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class BookModel {


    @NotNull(message = "Book name cannot be null.")
    @Size(min = 2, max = 100, message = "Book name must be between 2 and 100 characters.")
    @Pattern(regexp = "^[a-zA-Z\\s\\p{Punct}]+$", message = "Book name should contain only letters, spaces, and punctuation.")
    private String bookName;

    @NotNull()
    @Pattern(regexp = "^[a-zA-Z\\s]+$",message =  "Author cannot be blank.")
    @Size(min = 2, max = 50, message = "Author name must be between 2 and 50 characters.")
    private String author;

    @NotNull
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "Publish date must be in the format YYYY-MM-DD.")
    private String publishDate;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Prices must be a valid number with up to 2 decimal places.")
    private String prices;

    @NotNull(message = "Publisher company cannot be blank.")
    @Size(min = 2, max = 50, message = "Publisher company name must be between 2 and 50 characters.")
    private String publisherCompany;

    @Pattern(regexp = "^(ACTIVE|active|INACTIVE|inactive)$", message = "Status must be either 'ACTIVE' or 'INACTIVE'.")
    private String status;

    private String publishMonth;


    public void setPublishMonth(String publishMonth) {
        this.publishMonth = publishMonth;
    }
    public String getPublishMonth(){
        return publishMonth;
    }



    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getPublisherCompany() {
        return publisherCompany;
    }

    public void setPublisherCompany(String publisherCompany) {
        this.publisherCompany = publisherCompany;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
