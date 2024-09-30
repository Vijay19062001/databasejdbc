package springdemo.databasejdbc.entities;

import jakarta.persistence.*;

import springdemo.databasejdbc.enums.DBStatus;
import springdemo.databasejdbc.enums.RetentionStatus;

import java.time.LocalDate;

@Entity
@Table(name = "retentions")
public class RetentionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "created_date",nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date",nullable = false)
    private LocalDate updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "returns",nullable = false)
    private RetentionStatus returns;

    @Enumerated(EnumType.STRING)
    @Column(name = "db_status",nullable = false)
    private DBStatus dbStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id",nullable = false)
    private Books book;


    // Constructors, getters, and setters
    public RetentionEntity() {}

    public RetentionEntity( String name, LocalDate borrowDate, LocalDate returnDate, RetentionStatus returns,DBStatus dbStatus) {

        this.name = name;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returns = returns;
        this.dbStatus = dbStatus;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;

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

    public RetentionStatus getReturns() {
        return returns;
    }

    public void setReturns(RetentionStatus returns) {
        this.returns = returns;
    }

    public DBStatus getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(DBStatus dbStatus) {
        this.dbStatus = dbStatus;
    }


    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
