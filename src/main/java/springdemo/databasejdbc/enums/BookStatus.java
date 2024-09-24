package springdemo.databasejdbc.enums;

public enum BookStatus {

  ACTIVE("Active"),
    INACTIVE("InActive");


    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    }




