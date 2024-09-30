package springdemo.databasejdbc.model;

import java.util.List;

public class BookRetentionModel {

    private BookModel bookList;
    private List<RetentionModel> retentionList;


    public BookRetentionModel(BookModel bookList, List<RetentionModel> retentionList){

        this.bookList = bookList;
        this.retentionList = retentionList;
    }

    public BookModel getBookList() {
        return bookList;
    }

    // Getter for retentionList
    public List<RetentionModel> getRetentionList() {
        return retentionList;
    }

}
