package springdemo.databasejdbc.entities;


import jakarta.persistence.*;
import java.time.LocalDate;



import springdemo.databasejdbc.enums.BookStatus;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book_name")

    private String bookName;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "prices")
    private Double prices;

    @Column(name = "publisher_company")
    private String publisherCompany;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status;


    @Transient
    public  String publishMonth;        // To store the month, transient so it's not stored in the DB

    // Constructors, getters, and setters
    public Book() {}

    public Book(String bookName, String author, LocalDate publishDate, Double prices, String publisherCompany, BookStatus status) {
        this.bookName = bookName;
        this.author = author;
        this.publishDate = publishDate;
        this.prices = prices;
        this.publisherCompany = publisherCompany;
        this.status = status;
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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public String getPublisherCompany() {
        return publisherCompany;
    }

    public void setPublisherCompany(String publisherCompany) {
        this.publisherCompany = publisherCompany;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }


    public void setPublishMonth( String publishMonth){

        this.publishMonth = publishMonth;
    }

    @Transient
    public int getPublishMonth() {

        if (publishDate != null) {
            return publishDate.getMonthValue(); // Returns the month as a Month enum (e.g., JANUARY, FEBRUARY)
        } else {
            return Integer.parseInt((null)); // Return null if publishDate is not set
        }


    }



}
