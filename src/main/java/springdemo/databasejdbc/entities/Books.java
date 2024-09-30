package springdemo.databasejdbc.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import springdemo.databasejdbc.enums.BookStatus;

@Entity
@Table(name = "book")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "prices", nullable = false)
    private Double prices;

    @Column(name = "publisher_company", nullable = false)
    private String publisherCompany;

    @Column(name = "created_date",nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date",nullable = false)
    private LocalDate updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status;

    @Transient
    public String publishMonth;        // To store the month, transient so it's not stored in the DB


    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RetentionEntity> retentionEntity;


    // Constructors, getters, and setters
    public Books() {
    }

    public Books(String bookName, String author, LocalDate publishDate, Double prices, String publisherCompany, BookStatus status) {
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


    public void setPublishMonth(String publishMonth) {

        this.publishMonth = publishMonth;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<RetentionEntity> getRetentionEntity() {
        return retentionEntity;
    }

    public void setRetentionEntity(List<RetentionEntity> retentionEntity) {
        this.retentionEntity = retentionEntity;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }


    @Transient
    public int getPublishMonth() {

        if (publishDate != null) {
            return publishDate.getMonthValue();
        } else {
            return Integer.parseInt((null));
        }


    }


}
