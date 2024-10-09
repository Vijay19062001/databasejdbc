package springdemo.databasejdbc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RetentionModel {


    @JsonIgnore
    private String id;

    private String bookId;

    private String userId;

    @NotNull
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces.")
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "Borrow date must be in the format YYYY-MM-DD.")
    private String borrowDate;

    @NotNull
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "Return date must be in the format YYYY-MM-DD.")
    private String returnDate;

    @NotNull
    @Pattern(regexp = "^(EARLY_RETURN|early_return|LATE_RETURN|late_return|ON_TIME|on_time)$", message = "Returns status must be 'EARLY_RETURN','LATE_RETURN','ON_TIME'." )
    private String returns;

    @NotNull
    @Pattern(regexp = "^(ACTIVE|active|INACTIVE|inactive|PENDING|pending)$", message = "Status must be 'ACTIVE', 'INACTIVE'.")
    private String dbStatus;

    @NotNull
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "Created date must be in the format YYYY-MM-DD.")
    private String createdDate;

    @NotNull
    @Pattern(regexp = "^\\d{4}\\d{2}\\d{2}$", message = "Created date must be in the format YYYY-MM-DD.")
    private String updatedDate;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }


    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
