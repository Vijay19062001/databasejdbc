package springdemo.databasejdbc.entities;


import jakarta.persistence.*;

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
    private String publishDate;

    @Column(name = "prices")
    private Double prices;

    @Column(name = "publisher_company")
    private String publisherCompany;

    // Constructors, getters, and setters
    public Book() {}

    public Book(String bookName, String author, String publisheddate, Double prices, String publisherCompany) {
        this.bookName = bookName;
        this.author = author;
        this.publishDate = publisheddate;
        this.prices = prices;
        this.publisherCompany = publisherCompany;
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

    public double getPrices() {
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

}
