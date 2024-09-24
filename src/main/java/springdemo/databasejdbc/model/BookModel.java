package springdemo.databasejdbc.model;



import springdemo.databasejdbc.exception.basicexception.BasicValidationException;

import java.util.regex.Pattern;


public class BookModel  {


    private String bookName;
    private String author;
    private String publishDate;
    private String prices;
    private String publisherCompany;
//    private String createdDate;
//    private String updatedDate;
    private String status;


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

    public String  getPrices() {
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


//    public String getUpdatedDate() {return updatedDate;}
//
//    public void setUpdatedDate(String updatedDate) {this.updatedDate = updatedDate;}
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void validate() throws BasicValidationException {
        // Validate bookName (2-100 characters long, only letters, spaces, and hyphens allowed)
        if (!Pattern.matches("^[a-zA-Z\\s\\-]{2,100}$", bookName)) {
            throw new BasicValidationException("Book name must be 2-100 characters long and contain only letters, spaces, and hyphens.");
        }


        if (!Pattern.matches("^[a-zA-Z\\s]{2,50}$", author)) {
            throw new BasicValidationException("Author name must be 2-50 characters long and contain only letters and spaces.");
        }

        if (!Pattern.matches("^\\d{4}\\d{2}\\d{2}$", publishDate)) {
            throw new BasicValidationException("Publish date must be in the  YYYYMMDD.");
        }

//        if (!Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", createdDate)) {
//            throw new BasicValidationException("Created date must be in the format YYYY-MM-DD.");
//        }
//        if (!Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", updatedDate)) {
//            throw new BasicValidationException("Updated date must be in the format YYYY-MM-DD.");
//        }

        // Validate prices (numeric value with up to 2 decimal places)
        if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", prices)) {
            throw new BasicValidationException("Price must be a valid number with up to 2 decimal places.");
        }

        // Validate publisherCompany (2-50 characters long, only letters, spaces, and hyphens allowed)
        if (!Pattern.matches("^[a-zA-Z\\s\\-]{2,50}$", publisherCompany)) {
            throw new BasicValidationException("Publisher company must be 2-50 characters long and contain only letters, spaces, and hyphens.");
        }


    }
}

