package springdemo.databasejdbc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RetentionEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false) // Specify the foreign key
    private Book book; // Reference to the Book entity

    @Column(name = "name")
    private String name;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    // Constructors, getters, and setters
    public RetentionEntity() {}

    public RetentionEntity(Book book, String name, LocalDate borrowDate, LocalDate returnDate) {
        this.book = book;
        this.name = name;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
}
