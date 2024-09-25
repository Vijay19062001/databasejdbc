package springdemo.databasejdbc.model;

import java.time.LocalDate;

public class RetentionModel {



    private String id;
    private String book;
    private String name;
    private LocalDate borrowDate;
    private LocalDate returnDate;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
