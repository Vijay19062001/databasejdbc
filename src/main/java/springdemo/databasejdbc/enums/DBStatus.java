package springdemo.databasejdbc.enums;

public enum DBStatus {

    ACTIVE("Active"),
    INACTIVE("InActive");


    private final String dbStatus;

    DBStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

}




