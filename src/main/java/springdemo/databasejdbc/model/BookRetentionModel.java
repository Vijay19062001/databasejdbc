package springdemo.databasejdbc.model;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class BookRetentionModel {

    @JsonPropertyOrder({ "id","bookName","retensModel"})
    private Long id;
    private String bookName;
    private RetentionModel retensModel;

    public BookRetentionModel(long id, String bookName, RetentionModel dModel){
        this.id = id;
        this.bookName = bookName;
        this.retensModel = dModel;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RetentionModel getRetensModel() {
        return retensModel;
    }

    public void setRetensModel(RetentionModel retensModel) {
        this.retensModel = retensModel;
    }
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }




}
